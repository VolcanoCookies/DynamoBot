package com.github.volcanocookies.dynamo2.ReactionListeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import com.github.volcanocookies.dynamo2.Main;

public class NicknameRequestListener implements ReactionAddListener {

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        if(event.getReaction().get().getEmoji().asUnicodeEmoji().get().equals("✅") && event.getUser().isBotOwner()){
        	try {
            	Class.forName("com.mysql.jdbc.Driver").newInstance();
    			Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
    			Statement statement = connection.createStatement();
    			ResultSet result = statement.executeQuery("select * from NICKNAMECHANGEREQUEST");
    			while(result.next()) {
    				if(result.getString(1).equals(event.getMessage().get().getEmbeds().get(0).getFields().get(0).getValue())) {
    					System.out.println(Main.getUpdater().setNickname(event.getApi().getUserById(result.getString(1)).get(), "test").update().isCompletedExceptionally());
    					PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM NICKNAMECHANGEREQUEST WHERE USERID = \'" + 
    					result.getString(1) + "\'");
    	    			preparedStatement.execute();
    				}
    			}
    			connection.close();
    			event.getMessage().get().delete();
    		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | InterruptedException | ExecutionException e) {
    			e.printStackTrace();
    		}
        }
    }
}
