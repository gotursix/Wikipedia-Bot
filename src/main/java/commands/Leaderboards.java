package commands;

import dao.RecordsDao;
import helper.Config;
import model.Record;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leaderboards extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split("\\s+");
        if (message[0].equalsIgnoreCase(Config.prefix + "leaderboards")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("☢ Top users ☢");
            RecordsDao records = new RecordsDao();
            List<Record> toDisplay = new ArrayList<>();
            if (message.length == 2) {
                toDisplay = records.getAll(Math.max(1, Integer.parseInt(message[1])));
            } else {
                toDisplay = records.getAll(10);
            }
            for (int i = 0; i < toDisplay.size(); i++) {
                info.addField("✅ Rank: " + (i + 1), "<@" + toDisplay.get(i).getUser_id() + "> - lvl " + toDisplay.get(i).getXp() / 10, false);
            }
            info.setColor(0xf45642);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}
