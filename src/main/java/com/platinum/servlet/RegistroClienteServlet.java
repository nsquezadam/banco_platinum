package com.platinum.servlet;

import com.platinum.model.Persona;
import com.platinum.model.Usuario;
import com.platinum.model.CtaCorriente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RegistroClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Configuración BD
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";     
    private static final String JDBC_PASS = "mc_pass_123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Crear objetos modelo a partir de los parámetros del formulario
        Persona persona = new Persona(
            request.getParameter("rut"),
            request.getParameter("nombre"),
            request.getParameter("apellido"),
            request.getParameter("direccion"),
            request.getParameter("correo"),
            request.getParameter("telefono"),
            request.getParameter("nombreMascota")
        );

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getParameter("usuario"));
        usuario.setPassword(request.getParameter("password")); 

        CtaCorriente cuenta = new CtaCorriente();
        cuenta.setRutCliente(persona.getRut());
        cuenta.setMonto(0.0);                  
        cuenta.setEjecutivo("11111111-1");     
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // 2. Insertar persona
            String sqlPersona = "INSERT INTO persona (rut, nombre, apellido, direccion, correo, telefono, nombreMascota) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPersona = conn.prepareStatement(sqlPersona);
            psPersona.setString(1, persona.getRut());
            psPersona.setString(2, persona.getNombre());
            psPersona.setString(3, persona.getApellido());
            psPersona.setString(4, persona.getDireccion());
            psPersona.setString(5, persona.getCorreo());
            psPersona.setString(6, persona.getTelefono());
            psPersona.setString(7, persona.getNombreMascota());
            psPersona.executeUpdate();

            // 3. Insertar usuario
            String sqlUsuario = "INSERT INTO usuario (nombre_usuario, password) VALUES (?, ?)";
            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getPassword());
            psUsuario.executeUpdate();

            // 4. Insertar cuenta corriente
            String sqlCuenta = "INSERT INTO cta_corriente (rut_cliente, monto, ejecutivo) VALUES (?, ?, ?)";
            PreparedStatement psCuenta = conn.prepareStatement(sqlCuenta);
            psCuenta.setString(1, cuenta.getRutCliente());
            psCuenta.setDouble(2, cuenta.getMonto());
            psCuenta.setString(3, cuenta.getEjecutivo());
            psCuenta.executeUpdate();

            conn.close();

            request.setAttribute("mensaje", "Cliente, usuario y cuenta corriente registrados correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar cliente: " + e.getMessage());
        }

        // 5. Redirigir de nuevo al formulario con el mensaje
        request.getRequestDispatcher("registro-cliente.jsp").forward(request, response);
    }
}
