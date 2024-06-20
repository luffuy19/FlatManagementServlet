package com.chainsys.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.User;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
@MultipartConfig
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteServlet() {
        super();
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("deleteId"));
		TrancistionDto dto = new TrancistionDto();
		try {
			dto.deleteTenant(id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		response.sendRedirect("SearchTenantServlet");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("users");
		Part filePart = request.getPart("newPhoto");
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
        TrancistionDto trancistionDto = new TrancistionDto();
        try {
			trancistionDto.updatePhoto(imageBytes,user.getId());
			response.sendRedirect("SearchTenantServlet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
