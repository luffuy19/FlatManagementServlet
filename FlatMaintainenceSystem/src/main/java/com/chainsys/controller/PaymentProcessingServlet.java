package com.chainsys.controller;
import java.io.IOException;
import java.io.PrintWriter;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        User user=(User) session.getAttribute("users");
        TrancistionDto dto = new TrancistionDto();
        boolean hasPaid = false;
		try {
			hasPaid = dto.checkUserPayment(user.getId());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        if (!hasPaid) {
            System.out.println("yes");
        } else {
            out.println("<h2>You have already paid for this month.</h2>");
        }
    }
}
