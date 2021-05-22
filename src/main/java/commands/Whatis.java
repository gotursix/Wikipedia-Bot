package commands;

import dao.RecordsDao;
import helper.Config;
import helper.Helper;
import model.Record;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Whatis extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split("\\s+");
        if (message[0].equalsIgnoreCase(Config.prefix + "whats") && message.length >= 2) {
            List<String> list = new ArrayList<String>(Arrays.asList(message));
            list.remove(0);
            String listString = list.stream().map(Object::toString)
                    .collect(Collectors.joining(" "));
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("☢ What's " + listString + "☢");
            String text = Objects.requireNonNull(Helper.generateText(listString));
            Record displayLevelIncreased = null;
            if (text.length() > 1000) {
                int end = Helper.truncate(text, 1000);
                text = text.substring(0, Math.min(end, text.length()));
                info.addField("✅ Meanings: ", text + ".", false);
                RecordsDao recordsDao = new RecordsDao();
                Record r = recordsDao.get(event.getMessage().getAuthor().getId().toString());
                if (r != null) {
                    //update
                    r.setXp(r.getXp() + 1);
                    if (r.getXp() % 10 == 0)
                        displayLevelIncreased = r;
                    recordsDao.update(r, new String[]{String.valueOf(r.getXp())});
                } else {
                    //insert
                    r = new Record(event.getMessage().getAuthor().getId().toString(), 1);
                    recordsDao.save(r);
                }
            } else {
                info.addField("✅ Meanings: ", text.strip() + ".", false);
            }
            info.setColor(0xf45642);
            info.setFooter("✅ Question asked by @" + event.getMessage().getAuthor().getName(), event.getMessage().getAuthor().getAvatarUrl());
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();

            if (displayLevelIncreased != null) {
                event.getChannel().sendTyping().queue();
                info.setTitle("☢ User level increased ☢");
                info.setDescription("<@" + displayLevelIncreased.getUser_id() + "> has reached level " + displayLevelIncreased.xp / 10 + "!");
                info.setColor(0xf45642);
                event.getChannel().sendMessage(info.build()).queue();
                info.clear();
            }

        }
    }
}
