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
import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.Tenant;

@WebServlet("/TenantServlet")
@MultipartConfig
public class TenantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TenantServlet() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
        String phoneNo = request.getParameter("phoneNo");
        String email = request.getParameter("email");
        int userId=0;
        try {
			 userId = TrancistionDto.findUserId(email);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
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
        int familyMember = Integer.parseInt(request.getParameter("familyMembers"));
        String aadhaarNumber = request.getParameter("aadhaarNumber");
        int advanceAmount = Integer.parseInt(request.getParameter("advanceAmount"));
        String advancePaid = request.getParameter("advanceStatus");
        int rentAmount = Integer.parseInt(request.getParameter("rentAmount"));
        String flatType = request.getParameter("flatType");
        String flatFloor = request.getParameter("floorNumber");
        String roomNO = request.getParameter("roomNo");
        String dateOfJoining = request.getParameter("dateOfJoining");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in");
            return;
        }
        Tenant tenant = new Tenant(0, name, phoneNo, email, aadhaarNumber, imageBytes, familyMember, flatType, flatFloor,roomNO,advanceAmount, advancePaid, rentAmount, null, 0, null, dateOfJoining, null, null,userId);

        FlatMaintainenceDao flatMaintainenceDao = new FlatMaintainenceDao();
        try {
            int row = flatMaintainenceDao.addTenant(tenant);
            if(row>0) {
            	response.sendRedirect("addTenant.jsp");
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

