package com.studentcrud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionutil {
	public static Connection getConnections() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return  DriverManager.getConnection("jdbc:mysql://localhost:3306/flat_management", "root", "mani1952001");
	}
}
