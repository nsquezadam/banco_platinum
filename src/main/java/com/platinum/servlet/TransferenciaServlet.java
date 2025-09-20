package com.platinum.servlet;

import com.platinum.model.Transaccion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TransferenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

 // Configuración BD
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";     
    private static final String JDBC_PASS = "mc_pass_123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rutCliente = request.getParameter("rutCliente");
        String rutDueno = request.getParameter("rutDueno");
        int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
        double monto = Double.parseDouble(request.getParameter("monto"));
        String cuentaDestino = request.getParameter("cuentaTransferencia");
        String tipoCuenta = request.getParameter("tipoCuenta");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // 1. Verificar saldo de la cuenta origen
            String sqlSaldo = "SELECT monto FROM cta_corriente WHERE idCuenta = ? AND rut_cliente = ?";
            PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
            psSaldo.setInt(1, idCuenta);
            psSaldo.setString(2, rutCliente);
            ResultSet rs = psSaldo.executeQuery();

            if (!rs.next()) {
                request.setAttribute("mensaje", "Cuenta no encontrada.");
                request.getRequestDispatcher("transferencia.jsp").forward(request, response);
                return;
            }

            double saldoActual = rs.getDouble("monto");
            if (saldoActual < monto) {
                request.setAttribute("mensaje", "Fondos insuficientes. Saldo actual: " + saldoActual);
                request.getRequestDispatcher("transferencia.jsp").forward(request, response);
                return;
            }

            // 2. Descontar saldo
            String sqlUpdate = "UPDATE cta_corriente SET monto = monto - ? WHERE idCuenta = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setDouble(1, monto);
            psUpdate.setInt(2, idCuenta);
            psUpdate.executeUpdate();

            // 3. Registrar transacción
            String sqlInsert = "INSERT INTO transaccion (rutCliente, rutDueno, idCuenta, montoTransferencia, cuentaTransferencia, tipoCuenta) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, rutCliente);
            psInsert.setString(2, rutDueno);
            psInsert.setInt(3, idCuenta);
            psInsert.setDouble(4, monto);
            psInsert.setString(5, cuentaDestino);
            psInsert.setString(6, tipoCuenta);
            psInsert.executeUpdate();

            conn.close();

            // Crear objeto Transaccion como modelo
            Transaccion trx = new Transaccion(0, rutCliente, rutDueno, idCuenta, monto, cuentaDestino, tipoCuenta);
            request.setAttribute("mensaje", "Transferencia realizada con éxito por $" + monto);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al realizar transferencia: " + e.getMessage());
        }

        request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }
}
