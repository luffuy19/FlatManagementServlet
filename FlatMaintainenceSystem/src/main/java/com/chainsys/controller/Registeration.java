package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dao.FlatMaintainenceDao;
import com.chainsys.model.User;

@WebServlet("/Registeration")
public class Registeration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Registeration() {
        super();
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setEmail(request.getParameter("registerEmail"));
		user.setPassword(request.getParameter("registerConfirmPassword"));
		FlatMaintainenceDao registerationDao = new FlatMaintainenceDao();
		try {
			if(registerationDao.registerDao(user)==1) {
				response.sendRedirect("index.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
