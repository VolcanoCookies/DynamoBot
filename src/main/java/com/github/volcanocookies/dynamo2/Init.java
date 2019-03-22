package com.github.volcanocookies.dynamo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Models.WelcomeMessage;

public class Init {

    Init() {
    	try {
    		//Load channels
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from CHANNELS");
			while(result.next()) {
				if(result.getString(2).equals("nickrequestchannel")) {
					System.out.println(result.getString(1) + "\t" + result.getString(2));
					Main.setNicknameRequestChannel(Main.getAPI().getChannelById(result.getString(1)).get().asTextChannel().get());
				}
				if(result.getString(2).equals("bugreportchannel")) {
					System.out.println(result.getString(1) + "\t" + result.getString(2));
					Main.setReportChannel(Main.getAPI().getChannelById(result.getString(1)).get().asTextChannel().get());
				}
			}
			//Load messages
			System.out.println("Loading constant messages for server.");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			statement = connection.createStatement();
			result = statement.executeQuery("select * from MESSAGES");
			while(result.next()) {
				if(result.getString(2).equalsIgnoreCase("welcomemessage")) {
					System.out.println("Welcome message for server with id: " + result.getLong(2) + " is: " + result.getString(3));
					Main.getWelcomeMessages().add(new WelcomeMessage(result.getLong(2), result.getString(3)));
				}
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
