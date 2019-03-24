package com.github.volcanocookies.dynamo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Models.ServerObject;

public class Init {

    Init() {
    	try {
    		//Load channels
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://185.242.115.40/s1421_database", "u1421_yEFrXZhL3l", "qkPjkDUA4bZYX53y");
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from CHANNELS");
			while(result.next()) {
				ServerObject server = new ServerObject();
				server.setServerID(result.getString(1));
				server.setServer(Main.getAPI().getServerById(result.getString(1)).get());
				if(result.getString(2).equalsIgnoreCase("nicknamerequest"))
					server.setNicknameRequestChannelID(result.getString(3));
				if(result.getString(2).equalsIgnoreCase("bugreport"))
					server.setBugReportChannelID(result.getString(3));
				if(result.getString(2).equalsIgnoreCase("modhelp"))
					server.setModHelpChannelID(result.getString(3));
				if(result.getString(2).equalsIgnoreCase("welcome"))
					server.setWelcomeChannelID(result.getString(3));
				Main.getServers().add(server);
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
