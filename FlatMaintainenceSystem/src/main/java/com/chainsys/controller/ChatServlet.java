package com.chainsys.controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/ChatDB";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mani1952001";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int lastMessageId = Integer.parseInt(request.getParameter("lastMessageId"));
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		List<Message> messages = new ArrayList<>();
		int newLastMessageId = lastMessageId;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(
						"SELECT Messages.id, Users.username, Messages.message FROM Messages INNER JOIN Users ON Messages.user_id = Users.id WHERE Messages.id > ? ORDER BY Messages.id")) {
			ps.setInt(1, lastMessageId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int messageId = rs.getInt("id");
					String username = rs.getString("username");
					String message = rs.getString("message");
					messages.add(new Message(username, message));
					newLastMessageId = messageId;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		StringBuilder plainTextResponse = new StringBuilder();
		for (Message msg : messages) {
			plainTextResponse.append(msg.username).append(": ").append(msg.msg).append("\n");
		}
		plainTextResponse.append("LAST_ID=").append(newLastMessageId);

		response.getWriter().write(plainTextResponse.toString());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String message = request.getParameter("message");

		if (username != null && !username.trim().isEmpty() && message != null && !message.trim().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
				int userId = getUserId(conn, username);
				if (userId == -1) {
					try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Users (username) VALUES (?)",
							Statement.RETURN_GENERATED_KEYS)) {
						ps.setString(1, username);
						ps.executeUpdate();
						try (ResultSet rs = ps.getGeneratedKeys()) {
							if (rs.next()) {
								userId = rs.getInt(1);
							}
						}
					}
				}

				try (PreparedStatement ps = conn
						.prepareStatement("INSERT INTO Messages (user_id, message) VALUES (?, ?)")) {
					ps.setInt(1, userId);
					ps.setString(2, message);
					ps.executeUpdate();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

	private int getUserId(Connection conn, String username) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM Users WHERE username = ?")) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		}
		return -1;
	}

	private static class Message {
		private String username;
		private String msg;

		public Message(String username, String message) {
			this.username = username;
			this.msg = message;
		}
	}
}
