package com.platinum.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        // Mensaje de confirmación
        request.setAttribute("mensajeExito", "Sesión cerrada correctamente");

        // Forward al login
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
