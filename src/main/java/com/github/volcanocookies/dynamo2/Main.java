package com.github.volcanocookies.dynamo2;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.ServerUpdater;

import com.github.volcanocookies.dynamo2.commands.CreateCustomEmbed;
import com.github.volcanocookies.dynamo2.commands.ModdingHelp;
import com.github.volcanocookies.dynamo2.objects.NicknameChangeRequest;
import com.github.volcanocookies.dynamo2mysqlhandlers.Connector;

import Models.ServerObject;
import Models.WelcomeMessage;

public class Main {

    //Add check for valid bot token, error print if no valid toker found

    //private static Logger logger = LogManager.getLogger(Main.class);
    public static TextChannel reportChannel;
    public static TextChannel nicknameRequestChannel;
    private static DiscordApi api;
    static final String CONFIG_PATH = "C:\\Users\\frane\\Desktop\\Dynamo Config.txt";
    static List<NicknameChangeRequest> nicknameChangeRequests = new ArrayList<NicknameChangeRequest>();
    static List<WelcomeMessage> welcomeMessages = new ArrayList<WelcomeMessage>();
    private static List<ServerObject> servers = new ArrayList<ServerObject>();
	private static ServerUpdater updater;

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = "NTUzMjc4MjQ2ODA4OTc3NDU4.D3hlWA.hb2U0XMXoFaZRyQMfynjA3OJiMQ";

        api = new DiscordApiBuilder().setToken(token).login().join();

        new Init();
        Connector connector = new Connector();
        
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
        
        // Add listeners
//        api.addMessageCreateListener(new ReportBug());
//        api.addMessageCreateListener(new SetReportChannel());
//        api.addMessageCreateListener(new ClearChannel());
//        api.addMessageCreateListener(new CustomEmbed());
//        api.addMessageCreateListener(new RequestNick());
//        api.addMessageCreateListener(new TestCommand());
        api.addMessageCreateListener(new ModdingHelp(connector));
//        api.addMessageCreateListener(new SetRequestNickChannel());
        api.addMessageCreateListener(new CreateCustomEmbed());
//        api.addMessageCreateListener(new Rainbow());
        for(Server server : api.getServers()) {
        	updater = server.createUpdater();
        }

        // Log a message, if the bot joined or left a server
//        api.addServerJoinListener(event -> logger.info("Joined server " + event.getServer().getName()));
//        api.addServerLeaveListener(event -> logger.info("Left server " + event.getServer().getName()));
    }
    public static DiscordApi getAPI(){
        return api;
    }
    public static String getConfigPath(){
        return CONFIG_PATH;
    }
    public static void addNicknameChangeRequest(NicknameChangeRequest request){
        nicknameChangeRequests.add(request);
    }
    public static List<NicknameChangeRequest> getNicknameChangeRequests() {
        return nicknameChangeRequests;
    }
    public static TextChannel getReportChannel() {
    	return reportChannel;
    }
    public static TextChannel getNicknameRequestChannel() {
    	return nicknameRequestChannel;
    }
    public static void setReportChannel(TextChannel reportChannel) {
		Main.reportChannel = reportChannel;
	}
    public static void setNicknameRequestChannel(TextChannel nicknameRequestChannel) {
		Main.nicknameRequestChannel = nicknameRequestChannel;
	}
    public static ServerUpdater getUpdater() {
		return updater;
	}
    public static List<WelcomeMessage> getWelcomeMessages() {
		return welcomeMessages;
	}
	public static List<ServerObject> getServers() {
		return servers;
	}
	public static void setServers(List<ServerObject> servers) {
		Main.servers = servers;
	}
}