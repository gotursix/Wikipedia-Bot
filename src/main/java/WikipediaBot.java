import commands.Help;
import commands.Leaderboards;
import commands.Level;
import commands.Whatis;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;


public class WikipediaBot {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createLight("YOUR_DISCORD_BOT_TOKEN", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .setActivity(Activity.playing("Type !help"))
                .build();
        jda.addEventListener(new Help());
        jda.addEventListener(new Level());
        jda.addEventListener(new Whatis());
        jda.addEventListener(new Leaderboards());
    }
}
