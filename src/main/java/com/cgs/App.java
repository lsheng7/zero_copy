package com.cgs;

import java.io.InputStream;

public class App {

	public static void main(String[] args) {
		InputStream is = App.class.getResourceAsStream("db.properties");
		System.out.println(is);
	}

}
