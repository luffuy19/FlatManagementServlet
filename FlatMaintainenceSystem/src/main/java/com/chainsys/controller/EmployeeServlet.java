package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Complain;
import com.chainsys.model.Employee;
import com.chainsys.model.User;

// Replace this with your actual database connection utility class

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the form data
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phone_number");
		String department = request.getParameter("department");
		String choice = request.getParameter("action");
		TrancistionDto dto = new TrancistionDto();
		switch (choice) {
		case "addEmployee":
			Employee employee = new Employee(0, name, phoneNumber, department);
			int rowsInserted = 0;
			try {
				rowsInserted = dto.AddEmployee(employee);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if (rowsInserted > 0) {
				response.sendRedirect("complain.jsp");
			} else {
				response.sendRedirect("error.jsp");
			}
			break;
		case "deleteEmployee":
			int taskId = Integer.parseInt(request.getParameter("employeeId"));
			try {
				dto.deleteEmployee(taskId);
				response.sendRedirect("complain.jsp");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
		case "assignWork":
			int workerId = Integer.parseInt(request.getParameter("workerId"));
			int complaintId = Integer.parseInt(request.getParameter("complaintId"));

			try {
				dto.addTask(workerId, complaintId);
				response.sendRedirect("complain.jsp?success=true");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("complain.jsp?success=false");
			}
			break;
		case "deleteTask":
			int complainId = Integer.parseInt(request.getParameter("complaintId"));
			try {
				dto.deleteTasksByComplainId(complainId);
				dto.deleteComplaint(complainId);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("complain.jsp");
			break;
		case "addTask":
			String complainType = request.getParameter("complain_type");
			String comments = request.getParameter("comments");
			String complainDate = request.getParameter("complain_date");
			String floorNo = request.getParameter("floor_no");
			String roomNo = request.getParameter("room_no");
			HttpSession s = request.getSession(false);
			User users = (User) s.getAttribute("users");
			Complain complain = new Complain(0, complainType, comments, complainDate, complainType, users.getId(), floorNo, roomNo);
			try {
				dto.addComplaint(complain);
				response.sendRedirect("complain.jsp");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
