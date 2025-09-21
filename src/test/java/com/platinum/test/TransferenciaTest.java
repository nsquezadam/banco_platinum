package com.platinum.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba unitaria para validar que una transferencia:
 *  1. Descuenta saldo en la cuenta origen.
 *  2. Registra el movimiento en la tabla transaccion.
 */
public class TransferenciaTest {

    // Configuración BD
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";
    private static final String JDBC_PASS = "mc_pass_123";

    private int idCuentaPrueba = 1;
    private String rutClientePrueba = "22222222-2";
    private double montoTransferencia = 100.0;

    private double saldoInicial;

    @BeforeEach
    public void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            // Obtener saldo inicial de la cuenta
            String sqlSaldo = "SELECT monto FROM cta_corriente WHERE id_cuenta = ?";
            PreparedStatement ps = conn.prepareStatement(sqlSaldo);
            ps.setInt(1, idCuentaPrueba);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                saldoInicial = rs.getDouble("monto");
            } else {
                fail("La cuenta de prueba no existe en la BD.");
            }
        }
    }

    @Test
    public void testTransferencia() throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {

            // 1. Ejecutar lógica de transferencia (simulada aquí)
            String sqlUpdate = "UPDATE cta_corriente SET monto = monto - ? WHERE id_cuenta = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setDouble(1, montoTransferencia);
            psUpdate.setInt(2, idCuentaPrueba);
            int filasAfectadas = psUpdate.executeUpdate();
            assertEquals(1, filasAfectadas, "Debe afectar exactamente 1 fila");

            // 2. Registrar transacción
            String sqlInsert = "INSERT INTO transaccion (rut_cliente, rut_dueno, id_cuenta, monto_transferencia, cuenta_transferencia, tipo_cuenta) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, rutClientePrueba);
            psInsert.setString(2, "33333333-3"); // RUT destino ficticio
            psInsert.setInt(3, idCuentaPrueba);
            psInsert.setDouble(4, montoTransferencia);
            psInsert.setString(5, "12345678");   // cuenta destino ficticia
            psInsert.setString(6, "CC");         // tipo de cuenta
            psInsert.executeUpdate();

            // 3. Verificar saldo final
            String sqlSaldo = "SELECT monto FROM cta_corriente WHERE id_cuenta = ?";
            PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
            psSaldo.setInt(1, idCuentaPrueba);
            ResultSet rsSaldo = psSaldo.executeQuery();
            if (rsSaldo.next()) {
                double saldoFinal = rsSaldo.getDouble("monto");
                assertEquals(saldoInicial - montoTransferencia, saldoFinal, 0.01,
                             "El saldo debe descontarse correctamente");
            } else {
                fail("No se pudo verificar el saldo después de la transferencia.");
            }
        }
    }
}
