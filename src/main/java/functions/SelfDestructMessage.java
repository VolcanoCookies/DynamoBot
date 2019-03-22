package functions;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public class SelfDestructMessage extends Thread {
	
	int timer;
	Message message;
	
	@Override
	public void run() {
		try {
			sleep(timer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.delete();
	}
	public SelfDestructMessage(String messageContent, TextChannel channel, int timer) {
		this.timer = timer;
		message = sendMessage(messageContent, channel);
		run();
	}
	Message sendMessage(String messageContent, TextChannel channel) {
		try {
			return channel.sendMessage(messageContent).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
}
