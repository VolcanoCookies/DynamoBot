package Models;

import org.javacord.api.entity.server.Server;

public class ServerObject {
	Server server;
	String serverID;
	String welcomeChannelID;
	String modHelpChannelID;
	String bugReportChannelID;
	String nicknameRequestChannelID;
	
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public String getServerID() {
		return serverID;
	}
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}
	public String getWelcomeChannelID() {
		return welcomeChannelID;
	}
	public void setWelcomeChannelID(String welcomeChannelID) {
		this.welcomeChannelID = welcomeChannelID;
	}
	public String getModHelpChannelID() {
		return modHelpChannelID;
	}
	public void setModHelpChannelID(String modHelpChannelID) {
		this.modHelpChannelID = modHelpChannelID;
	}
	public String getBugReportChannelID() {
		return bugReportChannelID;
	}
	public void setBugReportChannelID(String bugReportChannelID) {
		this.bugReportChannelID = bugReportChannelID;
	}
	public String getNicknameRequestChannelID() {
		return nicknameRequestChannelID;
	}
	public void setNicknameRequestChannelID(String nicknameRequestChannelID) {
		this.nicknameRequestChannelID = nicknameRequestChannelID;
	}
}
