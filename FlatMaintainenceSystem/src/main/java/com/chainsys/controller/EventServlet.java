package com.chainsys.controller;

import com.chainsys.model.Event;
import com.studentcrud.util.Connectionutil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Event> events = new ArrayList<>();
        try (Connection con = Connectionutil.getConnections()) {
            String sql = "SELECT id, title, description, event_date, location FROM events";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        String date = rs.getString("event_date");
                        String location = rs.getString("location");
                        events.add(new Event(id, title, description, date, location));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("events", events);
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            try (Connection con = Connectionutil.getConnections()) {
                String sql = "DELETE FROM events WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, eventId);
                    ps.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("EventServlet");
    }
}
