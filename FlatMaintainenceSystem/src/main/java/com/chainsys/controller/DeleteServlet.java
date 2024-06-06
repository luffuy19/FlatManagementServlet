package com.chainsys.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dto.TrancistionDto;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteServlet() {
        super();
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("deleteId"));
		System.out.println(request.getParameter("deleteId"));
		TrancistionDto dto = new TrancistionDto();
		try {
			dto.deleteTenant(id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		response.sendRedirect("search.jsp");
	}

}
