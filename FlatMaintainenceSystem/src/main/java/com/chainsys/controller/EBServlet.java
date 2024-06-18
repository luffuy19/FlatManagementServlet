package com.chainsys.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dao.FlatMaintainenceDao;


@WebServlet("/addEbBill")
public class EBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EBServlet() {
        super();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("tenantId"));
	        int unit = Integer.parseInt(request.getParameter("unit"));
	        FlatMaintainenceDao dao = new FlatMaintainenceDao();
	        try {
				dao.addEbBill(id,unit);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        response.sendRedirect("EBbillServlet");  
	}

}
