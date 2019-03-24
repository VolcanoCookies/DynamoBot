package com.github.volcanocookies.dynamo2.commands;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;
import com.github.volcanocookies.dynamo2mysqlhandlers.Connector;

import Models.ServerObject;

public class ModdingHelp implements MessageCreateListener {
	
	private Connector connector;

	public ModdingHelp(Connector connector) {
		this.connector = connector;
	}
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().toLowerCase().startsWith("!modhelp")) {
			String content = event.getMessageContent();
			if(content.split(" ").length<2) {
				try {
					//Wrong formating of command
					event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage(new EmbedBuilder()
							.setTitle("Command usage")
							.addField("Howto", "!modhelp <type of help> <question etc>")
							.addField("Types", "Case insensitive, only one, are as following:\nModding\nModeling\nMapmaking\nNPC\nplugins")
							.addField("Question", "Everything else someone needs to help you.")
							.setAuthor(event.getApi().getYourself())
							.setTimestamp(event.getMessage().getCreationTimestamp())
							.setColor(Color.red)
							);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(content.split(" ").length<3) {
				//Wrong formating of command
				try {
					event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage(new EmbedBuilder()
							.setTitle("Command usage")
							.addField("Howto", "!modhelp <type of help> <question etc>")
							.addField("Types", "Case insensitive, only one, are as following:\nModding\nModeling\nMapmaking\nNPC\nplugins")
							.addField("Question", "Everything else someone needs to help you.")
							.setAuthor(event.getApi().getYourself())
							.setTimestamp(event.getMessage().getCreationTimestamp())
							.setColor(Color.red)
							);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				for(ServerObject server : Main.getServers()) {
					if(server.getServerID().equalsIgnoreCase(event.getServer().get().getIdAsString()) && server.getModHelpChannelID()!=null) {
						String type = content.split(" ")[1];
						String controlString = type.toLowerCase();
						if(controlString.contains("modding")) {
							
						} else if (controlString.contains("modding")) {
							type = "Modding";
							event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(event.getServer().get().getRolesByNameIgnoreCase("modding support").get(0).getMentionTag());
						} else if (controlString.contains("modeling")) {
							type = "Modeling";
							event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(event.getServer().get().getRolesByNameIgnoreCase("model support").get(0).getMentionTag());
						} else if (controlString.contains("mapmaking")) {
							type = "Mapmaking";
							event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(event.getServer().get().getRolesByNameIgnoreCase("mapmaking support").get(0).getMentionTag());
						} else if (controlString.contains("npc")) {
							type = "NPCs";
							event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(event.getServer().get().getRolesByNameIgnoreCase("npc support").get(0).getMentionTag());
						} else if (controlString.contains("plugin")) {
							type = "Plugins";
							event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(event.getServer().get().getRolesByNameIgnoreCase("Plugin support").get(0).getMentionTag());
						} else {
							type = "Other";
						}
						event.getServer().get().getChannelById(server.getModHelpChannelID()).get().asTextChannel().get().sendMessage(new EmbedBuilder()
							.setTitle("Help request")
							.addField("User", event.getMessageAuthor().getDiscriminatedName() + " requires assistance.")
							.addField("Type", content.split(" ")[1])
							.addField("Question", content.split(content.split(" ")[1])[1])
							.setAuthor(event.getMessageAuthor())
							.setTimestamp(event.getMessage().getCreationTimestamp())
							.setColor(Color.YELLOW)
							);
						return;
					}
				}
				try {
					event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage(new EmbedBuilder()
							.setTitle("Error")
							.addField("Message", "Doesn't apprear as the server has a modhelp channel setup, contact your server admins to get this resolved, incase you are a server adming, contact Volcano#2343")
							.setAuthor(event.getApi().getOwner().get())
							.setTimestamp(event.getMessage().getCreationTimestamp())
							.setColor(Color.red)
							);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			event.getMessage().delete();
		} else if (event.getMessageContent().toLowerCase().startsWith("!setmodhelpchannel") && event.getMessageAuthor().canManageServer()) {
			connector.saveChannel(event.getServer().get().getIdAsString(), "modhelp", event.getChannel().getIdAsString());
			for(ServerObject server : Main.getServers()) {
				System.out.println(server.getServerID());
				System.out.println(event.getServer().get().getIdAsString());
				if(server.getServerID().equalsIgnoreCase(event.getServer().get().getIdAsString())) {
					server.setModHelpChannelID(event.getChannel().getIdAsString());
					System.out.println("MODDINGHELP: Saving channel id to existing server object.");
					event.getMessage().delete();
					return;
				}
			}
			ServerObject server = new ServerObject();
			server.setModHelpChannelID(event.getChannel().getIdAsString());
			server.setServer(event.getServer().get());
			server.setServerID(event.getServer().get().getIdAsString());
			Main.getServers().add(server);
			System.out.println("MODDINGHELP: Creating new serverobject.");
			event.getMessage().delete();
		}
	}
}
