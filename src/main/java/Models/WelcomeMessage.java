package Models;

public class WelcomeMessage {
	
	String messageContent;
	long serverId;
	
	public WelcomeMessage(long serverId, String messageContent) {
		this.messageContent = messageContent;
		this.serverId = serverId;
	}
	
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public long getServerId() {
		return serverId;
	}
	public void setServerId(long serverId) {
		this.serverId = serverId;
	}
}
