package com.platinum.servlet;

import com.platinum.model.Transaccion;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
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
        long idCuenta = Long.parseLong(request.getParameter("idCuenta"));
        double monto = Double.parseDouble(request.getParameter("monto"));
        String cuentaDestino = request.getParameter("cuentaTransferencia");
        String tipoCuenta = request.getParameter("tipoCuenta");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // 1. Verificar saldo
            String sqlSaldo = "SELECT monto FROM cta_corriente WHERE id_cuenta = ? AND rut_cliente = ?";
            PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
            psSaldo.setLong(1, idCuenta);
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

            // 2. Descontar saldo de cuenta origen
            String sqlUpdate = "UPDATE cta_corriente SET monto = monto - ? WHERE id_cuenta = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setDouble(1, monto);
            psUpdate.setLong(2, idCuenta);
            psUpdate.executeUpdate();

            // 3. Registrar transacción
            String sqlInsert = "INSERT INTO transaccion (rut_cliente, rut_dueno, id_cuenta, monto_transferencia, cuenta_transferencia, tipo_cuenta) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, rutCliente);
            psInsert.setString(2, rutDueno);
            psInsert.setLong(3, idCuenta);
            psInsert.setDouble(4, monto);
            psInsert.setString(5, cuentaDestino);
            psInsert.setString(6, tipoCuenta);
            psInsert.executeUpdate();

            // Cerrar recursos
            rs.close();
            psSaldo.close();
            psUpdate.close();
            psInsert.close();
            conn.close();

            // Crear objeto modelo opcional
            Transaccion trx = new Transaccion(0, rutCliente, rutDueno, idCuenta, monto, cuentaDestino, tipoCuenta);
            request.setAttribute("mensaje", "Transferencia realizada con éxito por $" + monto);
            request.setAttribute("transaccion", trx);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al realizar transferencia: " + e.getMessage());
        }

        request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }
}
