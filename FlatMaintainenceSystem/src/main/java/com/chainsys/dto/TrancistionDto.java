package com.chainsys.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chainsys.model.User;
import com.studentcrud.util.Connectionutil;

public class TrancistionDto {
	public int registerUser(User user) throws ClassNotFoundException, SQLException {
		Connection con = Connectionutil.getConnections();
		String query="INSERT INTO users (email, password) VALUES (?,?)";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1,user.getEmail());
		statement.setString(2,user.getPassword());
		return statement.executeUpdate();
	}
	public User loginDetails(String email) throws SQLException, ClassNotFoundException {
		Connection con = Connectionutil.getConnections();
		String query="SELECT id,email,password FROM users where email=?";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1,email);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			User user=new User();
			int id = Integer.parseInt(rs.getString("id"));
			user.setId(id);
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			return user;
		}
		return null;
	}
}
