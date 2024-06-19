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
import com.chainsys.model.User;

@WebServlet("/checkPayment")
public class PaymentProcessingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("users");
		TrancistionDto dto = new TrancistionDto();
		boolean hasPaid = false;
		try {
			hasPaid = dto.checkUserPayment(user.getId());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (!hasPaid) {
			response.sendRedirect("payment.jsp?payment=1");
		} else {
			response.sendRedirect("payment.jsp?payment=0");
		}

	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("users");
		TrancistionDto dto = new TrancistionDto();
		try {
			 dto.processPayment(user.getId());
			 doGet(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
