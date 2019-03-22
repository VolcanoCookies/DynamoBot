package com.github.volcanocookies.dynamo2.commands;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CreateCustomEmbed implements MessageCreateListener{

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().toLowerCase().contains("!createcustomembed")) {
			Thread thread = new Thread() {
				public void run() {
					EmbedBuilder embed = new EmbedBuilder();
					//Get add lines
					Matcher matcher = Pattern.compile(".addField\\(([^,]*), ([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						embed.addField(matcher.group(1), matcher.group(2), false);
					}
					matcher.reset();
					matcher = Pattern.compile(".setTitle\\(([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						embed.setTitle(matcher.group(1));
					}
					matcher.reset();
					matcher = Pattern.compile(".setAuthor\\(([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						embed.setAuthor(event.getMessage().getMentionedUsers().get(0));
					}
					matcher.reset();
					matcher = Pattern.compile(".setColor\\(([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						embed.setColor(Color.decode(matcher.group(1)));
					}
					matcher.reset();
					matcher = Pattern.compile(".setFooter\\(([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						embed.setFooter(matcher.group(1));
					}
					matcher.reset();
					matcher = Pattern.compile(".send\\(([^\\)]*)\\)").matcher(event.getMessageContent());
					while(matcher.find()) {
						event.getMessage().getMentionedChannels().get(0).sendMessage(embed);
					}
					event.getMessage().delete();
				}
			};
			thread.run();
		}
	}
}
