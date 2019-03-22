package com.github.volcanocookies.dynamo2.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.github.volcanocookies.dynamo2.Main;

public class SetRequestNickChannel implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // Check if the message content equals "!setnicknamerequestchannel"
                if (event.getMessageContent().toLowerCase().contains("!setnicknamerequestchannel")) {
                    if (event.getMessageAuthor().isBotOwner()) {
                        Main.nicknameRequestChannel = event.getChannel();
                        if(Main.nicknameRequestChannel.equals(event.getChannel())) {
                            CompletableFuture<Message> mes = event.getChannel().sendMessage("This channel is now the nickname request channel.");
                            try {
                                Thread.sleep(3000);
                                event.getMessage().delete();
                                mes.get().delete("Dynamo delete response message.");
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            try {
                            	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    			Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
                    			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CHANNELS VALUES (\'" + event.getChannel().getId() + "\', \'nickrequestchannel\')");
                    			preparedStatement.execute();
                    			connection.close();
                    			System.out.println("Saving nickname request channel in mysql database");
                            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                                mes = event.getChannel().sendMessage("Could not write to config.");
                                try {
                                    Thread.sleep(3000);
                                    event.getMessage().delete();
                                    mes.get().delete("Dynamo delete response message.");
                                } catch (InterruptedException | ExecutionException e2){
                                    e2.printStackTrace();
                                }
                                e.printStackTrace();
                            }
                        } else {
                            CompletableFuture<Message> mes = event.getChannel().sendMessage("Something went wrong, could not set nickname request channel.");
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
