package com.github.volcanocookies.dynamo2.ReactionListeners;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CommissionsListener implements MessageCreateListener {
	
	int receivedMessages;
	String[] messages = {"", "", "", ""};
	String[] responses = {"2. What will the item requested be used for." + 
			"3. What payment/compensation will the creator get." + 
			"4. Does the creator get credit and/or rights to his creation."};
	EmbedBuilder Embed;
	TextChannel channel;
	
	public CommissionsListener(TextChannel channel) {
		Embed = new EmbedBuilder();
		receivedMessages = 0;
		this.channel = channel;
	}
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().equalsIgnoreCase("!cancel")) {
			event.getChannel().sendMessage("You've cancelled your commission request");
			event.getApi().removeListener(this);
		} else if(event.getMessageContent().equalsIgnoreCase("!next")) {
			if(receivedMessages>=2) {
				
			}
			event.getChannel().sendMessage(responses[receivedMessages++]);
			
		} else {
			messages[receivedMessages] += event.getMessageContent() + "\n";
		}
	}
}
