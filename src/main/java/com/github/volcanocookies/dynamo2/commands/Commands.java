package com.github.volcanocookies.dynamo2.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Commands implements MessageCreateListener{
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().equalsIgnoreCase("!commands") || event.getMessageContent().equalsIgnoreCase("!help")) {
			event.getChannel().sendMessage("Current commands:\n"
					+ "!CreateCustomEmbed");
		}
	}
}
