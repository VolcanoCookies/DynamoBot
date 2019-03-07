package DynamoBot.DynamoBot;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import commands.ClearCommand;

/**
 * Hello world!
 *
 */
public class DynamoBot 
{
	static String TOKEN = "NTUzMjc4MjQ2ODA4OTc3NDU4.D2Lyvw.d1yeYuPvfRNlEKP0zPUbDR-jyYc";
	
	private static Logger logger = LogManager.getLogger(DynamoBot.class);
	
    public static void main(String[] args)
    {
    	FallbackLoggerConfiguration.setDebug(true);
    	
    	DiscordApi api = new DiscordApiBuilder().setToken(TOKEN).login().join();
    	
    	api.addMessageCreateListener(new ClearCommand());
    }
}
