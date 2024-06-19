package com.chainsys.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Visitor;

/**
 * Servlet implementation class VisitorServlet
 */
@WebServlet("/VisitorServlet")
public class VisitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VisitorServlet() {
        super();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TrancistionDto dto = new TrancistionDto();
		String day = request.getParameter("options");
		try {
			List<Visitor> viewVisitor = dto.viewVisitor(day);
			request.setAttribute("visitor", viewVisitor);
			request.getRequestDispatcher("visitor.jsp").forward(request, response);;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choice = request.getParameter("checkIn");
		switch (choice) {
		case "A": {
			String visitorName = request.getParameter("visitorName");
	        String inTime = request.getParameter("inTime");
	        String inDate = request.getParameter("inDate");
	        int visitorFloor = Integer.parseInt(request.getParameter("visitorFloor"));
	        String visitorRoomNo = request.getParameter("visitorRoomNo");
	        Visitor visitor = new Visitor(0, visitorName, inTime,null,inDate,null,visitorFloor,visitorRoomNo,null);
	        TrancistionDto dto = new TrancistionDto();
	        try {
				dto.addVisitor(visitor);
				doGet(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		case "B" : {
			int id =Integer.parseInt(request.getParameter("visitorId"));
			String outTime = request.getParameter("outTime");
			String outDate = request.getParameter("outDate");
			Visitor visitor = new Visitor(id, null, null,outTime,null,outDate,0,null, null);
			TrancistionDto dto = new TrancistionDto();
	        try {
				dto.outVisitor(visitor);
				doGet(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		}
	}

}
