package com.chainsys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Tenant;

@WebServlet("/EBbillServlet")
public class EBbillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int page = 1;
        int limit = 1; // Adjust the number of items per page as needed
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        TrancistionDto trancistionDto = new TrancistionDto();
        List<Tenant> allTenants = trancistionDto.searchTenants(query); // Fetch all tenants and filter
        int totalTenants = allTenants.size();
        
        // Calculate pagination details
        int totalPages = (int) Math.ceil((double) totalTenants / limit);
        int offset = (page - 1) * limit;
        
        // Get sublist based on offset and limit
        List<Tenant> tenantList = allTenants.subList(offset, Math.min(offset + limit, totalTenants));
        
        request.setAttribute("tenantList", tenantList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("query", query);
        request.getRequestDispatcher("ebbill.jsp").forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int page = 1;
        int limit = 1; // Adjust the number of items per page as needed
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        TrancistionDto trancistionDto = new TrancistionDto();
        List<Tenant> allTenants = trancistionDto.getAllTenants(); // Fetch all tenants and filter
        int totalTenants = allTenants.size();
        
        // Calculate pagination details
        int totalPages = (int) Math.ceil((double) totalTenants / limit);
        int offset = (page - 1) * limit;
        
        // Get sublist based on offset and limit
        List<Tenant> tenantList = allTenants.subList(offset, Math.min(offset + limit, totalTenants));
        
        request.setAttribute("tenantList", tenantList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("query", query);
        request.getRequestDispatcher("ebbill.jsp").forward(request, response);
    }
}



