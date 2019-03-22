package com.github.volcanocookies.dynamo2.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;

public class SetReportChannel implements MessageCreateListener {

    /*
     * This command can be used to display information about the user who used the command.
     * It's a good example for the MessageAuthor, MessageBuilder and ExceptionLogger class.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // Check if the message content equals "!userInfo"
                if (event.getMessageContent().toLowerCase().contains("!setbugreportchannel")) {
                    if (event.getMessageAuthor().isBotOwner()) {
                        Main.reportChannel = event.getChannel();
                        if(Main.reportChannel.equals(event.getChannel())) {
                            CompletableFuture<Message> mes = event.getChannel().sendMessage("This channel is now the bug report channel.");
                            try {
                                Thread.sleep(3000);
                                event.getMessage().delete();
                                mes.get().delete("Dynamo delete response message.");
                            } catch (InterruptedException | ExecutionException e){
                                e.printStackTrace();
                            }
                            try {
                            	String command = "INSERT INTO CHANNELS VALUES (\'" + event.getChannel().getId() + "\', \'bugreportchannel\')";
                            	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    			Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
                    			Statement statement = connection.createStatement();
                    			ResultSet result = statement.executeQuery("select * from CHANNELS");
                    			while(result.next()) {
                    				if(result.getString(2).equals("bugreportchannel")) {
                    					command = "UPDATE CHANNELS SET CHANNEL = \'" + event.getChannel().getId() + "\' WHERE TYPE = \'bugreportchannel\'";
                    					System.out.println("Channel already set, changing");
                    				}
                    					
                    			}
                    			Class.forName("com.mysql.jdbc.Driver").newInstance();
                    			PreparedStatement preparedStatement = connection.prepareStatement(command);
                    			preparedStatement.execute();
                    			connection.close();
                    			System.out.println("Saving report channel in mysql database");
                    		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    			CompletableFuture<Message> mes2 = event.getChannel().sendMessage("Could not save reportchannel to mysql database.");
                                try {
                                    Thread.sleep(3000);
                                    event.getMessage().delete();
                                    mes2.get().delete("Dynamo delete response message.");
                                } catch (InterruptedException | ExecutionException e2){
                                    e.printStackTrace();
                                }
                    			e.printStackTrace();
                    		}
                        } else {
                            CompletableFuture<Message> mes = event.getChannel().sendMessage("Something went wrong, could not set bug report channel.");
                            try {
                                Thread.sleep(3000);
                                event.getMessage().delete();
                                mes.get().delete("Dynamo delete response message.");
                            } catch (InterruptedException | ExecutionException e){
                                e.printStackTrace();
                            }
                        }
                    } else {
                        CompletableFuture<Message> mes = event.getChannel().sendMessage("You don't have sufficient permissions to use this command.");
                        try {
                            Thread.sleep(3000);
                            event.getMessage().delete();
                            mes.get().delete("Dynamo delete response message.");
                        } catch (InterruptedException | ExecutionException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.run();
    }
}
