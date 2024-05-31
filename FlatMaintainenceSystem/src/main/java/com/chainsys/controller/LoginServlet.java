package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setEmail(request.getParameter("loginEmail"));
		user.setPassword(request.getParameter("loginPassword"));
		String emailPattern = "^[a-zA-Z0-9._%+-]+@inam\\.com$";
		System.out.println(user.getEmail());
		FlatMaintainenceDao dao = new FlatMaintainenceDao();
		try {
			int loginCheck = dao.loginCheck(user);
			if(loginCheck==1) {
				HttpSession session = request.getSession();
				session.setAttribute("user_id", user.getId());
				if (Pattern.matches(emailPattern, user.getEmail())) {
		            System.out.println("send to admin page");
		        } else {
		            System.out.println("send to user page");
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
