package com.platinum.servlet;

import com.platinum.model.Ejecutivo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Configuración BD
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";
    private static final String JDBC_PASS = "mc_pass_123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rut = request.getParameter("rut");
        String departamento = request.getParameter("departamento");

        Ejecutivo ejecutivo = validarEjecutivo(rut, departamento);

        if (ejecutivo != null) {
            // Guardar ejecutivo en sesión
            request.getSession().setAttribute("ejecutivo", ejecutivo);

            // Redirigir al menú principal (index.jsp)
            response.sendRedirect("index.jsp");
        } else {
            // Guardar mensaje de error en request y volver a login.jsp con forward
            request.setAttribute("error", "Credenciales inválidas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Método que devuelve un Ejecutivo si las credenciales son válidas
    private Ejecutivo validarEjecutivo(String rut, String departamento) {
        Ejecutivo ejecutivo = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql = "SELECT rut_ejecutivo, nombre, departamento FROM ejecutivo WHERE rut_ejecutivo = ? AND departamento = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rut);
            ps.setString(2, departamento);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ejecutivo = new Ejecutivo(
                        rs.getString("rut_ejecutivo"),
                        rs.getString("nombre"),
                        rs.getString("departamento")
                );
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ejecutivo;
    }
}
