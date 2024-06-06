package com.chainsys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.chainsys.dao.FlatMaintainenceDao;
import com.chainsys.model.Tenant;

@WebServlet("/TenantServlet")
@MultipartConfig
public class TenantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TenantServlet() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
        String phoneNo = request.getParameter("phoneNo");
        String email = request.getParameter("email");
        System.out.println(email);
        Part filePart = request.getPart("photo");
        byte[] imageBytes = null;

        if (filePart != null) {
            try (InputStream inputStream = filePart.getInputStream()) {
                imageBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading uploaded file");
                return;
            }
        }
        String role=request.getParameter("role");
        int familyMember = Integer.parseInt(request.getParameter("familyMembers"));
        String aadhaarNumber = request.getParameter("aadhaarNumber");
        int advanceAmount = Integer.parseInt(request.getParameter("advanceAmount"));
        String advancePaid = request.getParameter("advanceStatus");
        int rentAmount = Integer.parseInt(request.getParameter("rentAmount"));
        String flatType = request.getParameter("flatType");
        String flatFloor = request.getParameter("floorNumber");
        String dateOfJoining = request.getParameter("dateOfJoining");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in");
            return;
        }
        Tenant tenant = new Tenant(0, name, phoneNo, email, aadhaarNumber, imageBytes, familyMember, flatType, flatFloor, advanceAmount, advancePaid, rentAmount, null, 0, null, dateOfJoining, null, null, 0);

        FlatMaintainenceDao flatMaintainenceDao = new FlatMaintainenceDao();
        try {
            int row = flatMaintainenceDao.addTenant(tenant);
            if(row>0) {
            	response.sendRedirect("addTenant?role="+role);
            }
            else {
            	response.sendRedirect("index.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding tenant to the database");
        }
    }
}

