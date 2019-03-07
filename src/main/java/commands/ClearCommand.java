package commands;

import java.awt.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ClearCommand implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		//Message content
		String message = event.getMessageContent();
		
		//Check for message content
		if (message.toLowerCase().contains("!clear")) {
			//Check if author has edit message rights
			if (!event.getMessageAuthor().canManageMessagesInTextChannel()) {
				return;
			}
			
			//Check if message contains enough arguments
			if (!(message.split(" ").length >= 2)) {
				return;
			}
			
			//Check if first argument is a Integer
			if (!message.split(" ")[1].chars().allMatch(Character::isDigit)) {
				return;
			}
			
			//Get number of messages to delete & channel
			int messagesToDelete = Integer.valueOf(message.split(" ")[1]);
			TextChannel channel = event.getChannel();
			MessageAuthor author;
			
			//Check if message specifies a author.
			if (message.split(" ").length>=3) {
				author = event.getMessageAuthor();
			}
			
			channel.deleteMessages(channel.getMessages(100));
		}

	}
}
