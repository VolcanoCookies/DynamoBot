package com.github.volcanocookies.dynamo2.commands;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;

public class CustomEmbed implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Check if the message content equals "!userInfo"
        if (event.getMessageContent().toLowerCase().contains("!customembed")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Media")
                    .addField("", "People who have received either the youtuber or streamer role.", true)
                    .addField("Youtuber","Self explanatory, of course moderate success with your channel is required.", true)
                    .addField("Streamer", "Self explanatory, of course moderate success with your channel is required.", true)
                    .setAuthor(event.getMessageAuthor().getDisplayName());
            event.getChannel().sendMessage(new EmbedBuilder()
            		.setTitle("Media")
            		.setDescription("People who have received either the youtuber or streamer role.")
            		.addField("Youtuber", "Self explanatory, of course moderate success with your channel is required.")
            		.addField("Streamer", "Self explanatory, of course moderate success with your channel is required.")
            		.setColor(Color.decode("#35ffa3"))
            		);
        }
    }
}
