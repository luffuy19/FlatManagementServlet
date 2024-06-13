package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Employee;

// Replace this with your actual database connection utility class

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		case "deleteEmployee" : 
			int taskId = Integer.parseInt(request.getParameter("employeeId"));
			try {
				dto.deleteEmployee(taskId);
				response.sendRedirect("complain.jsp");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
}
