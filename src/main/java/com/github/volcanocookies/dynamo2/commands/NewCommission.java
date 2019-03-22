package com.github.volcanocookies.dynamo2.commands;

import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.ReactionListeners.CommissionsListener;

public class NewCommission implements MessageCreateListener {
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().toLowerCase().contains("!newcommission")) {
			User user = event.getMessageAuthor().asUser().get();
			PrivateChannel privateChannel = user.getPrivateChannel().get();
			privateChannel.sendMessage("**You've requested to make a new commission request, type \"!cancel\" at any step to cancel.**");
			privateChannel.sendMessage("Simply respond here to the questions asked by the bot.\n"
					+ "\"When you are done with a question, type !next to progress to the next one.\n"
					+ "\"1. Provide a description of what you want made.\"");
			privateChannel.addMessageCreateListener(new CommissionsListener(event.getChannel())).removeAfter(30, TimeUnit.MINUTES);
		}
	}
}
