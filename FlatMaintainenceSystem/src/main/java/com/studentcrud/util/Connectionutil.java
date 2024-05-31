package com.studentcrud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionutil {
	public static Connection getConnections() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/flat_management";
		String userName = "root";
		String password = "mani1952001";
		return  DriverManager.getConnection(url, userName, password);
	}
}
