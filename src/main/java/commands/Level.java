package commands;

import dao.RecordsDao;
import helper.Config;
import model.Record;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Level extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] arg = message.getContentRaw().split("\\s+");
        if (arg[0].equalsIgnoreCase(Config.prefix + "level")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("☢ Your level ☢");
            RecordsDao recordsDao = new RecordsDao();
            Record r = recordsDao.get(message.getAuthor().getId());
            if (r != null)
                info.addField("✅ Level: ", "<@" + message.getAuthor().getId() + "> has level " + r.getXp() / 10 + "! ( " + (10 - r.getXp() % 10) + " xp remaining till next level )", false);
            else info.addField("✅ Level: ", "<@" + message.getAuthor().getId() + "> has level " + 0 + "!", false);
            info.setColor(0xf45642);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}
