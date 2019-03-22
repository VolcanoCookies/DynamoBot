package com.github.volcanocookies.dynamo2.commands;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;
import com.github.volcanocookies.dynamo2.ReactionListeners.NicknameRequestListener;

import functions.SelfDestructMessage;

public class RequestNick implements MessageCreateListener {

    Message message;
    /*
     * This command can be used to display information about the user who used the command.
     * It's a good example for the MessageAuthor, MessageBuilder and ExceptionLogger class.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageContent().toLowerCase().contains("!requestnick")) {
        	if(!event.getMessageAuthor().canChangeOwnNicknameOnServer()) {
        		new SelfDestructMessage("You can already set your own nickname", event.getChannel().asTextChannel().get(), 3000);
        	} else {
        		if(Main.getNicknameRequestChannel()==null) {
        			new SelfDestructMessage("No nickname request channel has been set, contact server administrators.", event.getChannel().asTextChannel().get(), 3000);
        			return;
        		}
		    	try {
		    		Message message = Main.getNicknameRequestChannel().sendMessage(new EmbedBuilder()
							.setTitle("Nickname change request")
							.setAuthor(event.getMessageAuthor())
							.setColor(Color.BLUE)
							.addField("User ID", event.getMessageAuthor().getIdAsString())
							.addField("Current server name", event.getMessageAuthor().getDisplayName())
							.addField("Requested nickname", event.getMessageContent().substring(event.getMessageContent().indexOf(" ")))
							).get();
		    		
					System.out.println("User requesting nickname change.");
		        	String command = "INSERT INTO NICKNAMECHANGEREQUEST VALUES (\'" + event.getMessageAuthor().getId() + "\', \'" + event.getMessageAuthor().getDisplayName() 
		        			+ "\', \'" + event.getMessageContent().substring(event.getMessageContent().indexOf(" ")) + "\', \'" + message.getId() + "\')";
		        	Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery("select * from NICKNAMECHANGEREQUEST");
					while(result.next()) {
						if(result.getString(1).equals(Long.toString(event.getMessageAuthor().getId()))) {
							command = "UPDATE NICKNAMECHANGEREQUEST SET requestedNickname = \'" + event.getMessageContent().substring(event.getMessageContent().indexOf(" ")) 
									+ "\' WHERE UserID = \'" 
						+ event.getMessageAuthor().getId() + "\'";
							System.out.println("User already has a nickname change request, updating it.");
						}
					}
					PreparedStatement preparedStatement = connection.prepareStatement(command);
					preparedStatement.execute();
					connection.close();
					System.out.println("Adding nickname change request to mysql database.");
					new SelfDestructMessage("Your request is awaiting confirmation.", event.getChannel().asTextChannel().get(), 3000);
					
					message.addReaction("✅");
					message.addReactionAddListener(new NicknameRequestListener());
					
					event.getMessage().delete();
				} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | InterruptedException | ExecutionException e) {
					new SelfDestructMessage("Could not save request to mysql database", event.getChannel().asTextChannel().get(), 3000);
					e.printStackTrace();
				}
        	}
        }
    }
}
