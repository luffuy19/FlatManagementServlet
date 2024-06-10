package com.chainsys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dao.FlatMaintainenceDao;
import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Tenant;

import javax.servlet.RequestDispatcher;

@WebServlet("/EBbillServlet")
public class EBbillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    int page = 1;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int limit = 1;
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        TrancistionDto trancistionDto = new TrancistionDto();
        int offset = (page - 1) * limit;
        List<Tenant> tenantList = trancistionDto.searchTenants(query, offset, limit);
        int totalTenants = trancistionDto.getTenantCount(query);
        int totalPages = (int) Math.ceil((double) totalTenants / limit);
        request.setAttribute("tenantList", tenantList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("query", query);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ebbill.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tenantId"));
        int unit = Integer.parseInt(request.getParameter("unit"));
        FlatMaintainenceDao dao = new FlatMaintainenceDao();
        try {
			dao.addEbBill(id,unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
        response.sendRedirect("EBbillServlet?query="+request.getParameter("query")+"&page="+page);  
    }
}



