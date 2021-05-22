package commands;

import helper.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Objects;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split("\\s+");
        System.out.println(Arrays.toString(message));
        if (message[0].equalsIgnoreCase(Config.prefix + "help")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("☢ Wikipedia Bot Information ☢");
            info.setDescription("☢ Wikipedia Bot is a bot created to help users find quick information based on a given word.\n\n How arguments work?\n ☢ [param] - optional param\n ☢ <param> - required param\n");
            info.addField("\nAvailable commands: ", "", true);
            info.addField("✅ !help", "Displays all the available commands and how to use them.", false);
            info.addBlankField(false);
            info.addField("✅ !whats <word>", "Displays information from Wikipedia about the given word, the word can be anything, from fruits to companies.", false);
            info.addBlankField(false);
            info.addField("✅ !level", "Displays the current level of the user. The level is increased as the user asks the bot about more things.", false);
            info.addBlankField(false);
            info.addField("✅ !leaderboards [number]", "Displays the top users, limited by the number, otherwise displays the top 10 users and their level.", false);
            info.setColor(0xf45642);
            info.setFooter("Created by Infernus", Objects.requireNonNull(event.getMember()).getUser().getAvatarUrl());
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}
