package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dao.FlatMaintainenceDao;
import com.chainsys.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setEmail(request.getParameter("loginEmail"));
		user.setPassword(request.getParameter("loginPassword"));
		System.out.println(user.getEmail());
		FlatMaintainenceDao dao = new FlatMaintainenceDao();
		try {
			User loginCheck = dao.loginCheck(user);
			if(loginCheck!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("users", loginCheck);
				if (loginCheck.getRole().equals("admin")) {
		            response.sendRedirect("home.jsp");
				}
				else {
					response.sendRedirect("home.jsp");
				}
			}
			else {
				response.sendRedirect("index.jsp?error=1");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
