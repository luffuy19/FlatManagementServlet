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

import javax.servlet.RequestDispatcher;

@WebServlet("/SearchTenantServlet")
public class SearchTenantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int page = 1;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
        dispatcher.forward(request, response);
    }
}

