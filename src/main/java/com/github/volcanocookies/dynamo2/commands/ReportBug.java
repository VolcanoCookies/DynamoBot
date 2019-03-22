package com.github.volcanocookies.dynamo2.commands;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;

public class ReportBug implements MessageCreateListener {

    /*
     * This command can be used to display information about the user who used the command.
     * It's a good example for the MessageAuthor, MessageBuilder and ExceptionLogger class.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().toLowerCase().contains("!reportbug")) {
        	if (Main.getReportChannel()!=null) {
        		Main.getReportChannel().sendMessage(new EmbedBuilder()
            			.setAuthor(event.getMessageAuthor())
            			.setTitle("Bug report")
            			.setColor(Color.RED)
            			.setTimestampToNow()
            			.setDescription(event.getMessageContent().substring(event.getMessageContent().indexOf(" ")))
            			);
        	}
        	event.getMessage().delete();
        }
    }
}
