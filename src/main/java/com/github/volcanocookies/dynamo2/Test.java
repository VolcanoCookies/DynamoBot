package com.github.volcanocookies.dynamo2;

import com.github.volcanocookies.dynamo2mysqlhandlers.Connector;


public class Test {

	public static void main(String[] args) {
		Connector connector = new Connector();
		connector.saveChannel("testing server", "normaltype", "anotherid");
	}
}
