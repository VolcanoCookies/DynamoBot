package com.github.volcanocookies.dynamo2.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class TestCommand implements MessageCreateListener {
    
	int i = 0;
	
	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageContent().toLowerCase().contains("!testcommand")){
            event.getChannel().sendMessage(Integer.toString(i++));
        }
    }
}
