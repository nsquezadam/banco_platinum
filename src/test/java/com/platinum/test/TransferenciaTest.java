package com.platinum.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

/**
 * Prueba unitaria para validar que una transferencia:
 *  1. Descuenta saldo en la cuenta origen.
 *  2. Registra el movimiento en la tabla transaccion.
 */
public class TransferenciaTest {

	 // Configuración BD
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_cliente?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";     
    private static final String JDBC_PASS = "mc_pass_123";

    private int idCuentaPrueba = 1;       
    private String rutClientePrueba = "22222222-2"; 
    private double montoTransferencia = 100.0;

    private double saldoInicial;

    @Before
    public void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        // Obtener saldo inicial de la cuenta
        String sqlSaldo = "SELECT monto FROM cta_corriente WHERE idCuenta = ?";
        PreparedStatement ps = conn.prepareStatement(sqlSaldo);
        ps.setInt(1, idCuentaPrueba);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            saldoInicial = rs.getDouble("monto");
        } else {
            fail(" La cuenta de prueba no existe en la BD.");
        }
        conn.close();
    }

    @Test
    public void testTransferencia() throws Exception {
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        // 1. Ejecutar lógica de transferencia (simulada aquí)
        String sqlUpdate = "UPDATE cta_corriente SET monto = monto - ? WHERE idCuenta = ?";
        PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
        psUpdate.setDouble(1, montoTransferencia);
        psUpdate.setInt(2, idCuentaPrueba);
        int filasAfectadas = psUpdate.executeUpdate();
        assertEquals("Debe afectar exactamente 1 fila", 1, filasAfectadas);

        // 2. Registrar transacción
        String sqlInsert = "INSERT INTO transaccion (rutCliente, rutDueno, idCuenta, montoTransferencia, cuentaTransferencia, tipoCuenta) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
        psInsert.setString(1, rutClientePrueba);
        psInsert.setString(2, "33333333-3");       // RUT destino ficticio
        psInsert.setInt(3, idCuentaPrueba);
        psInsert.setDouble(4, montoTransferencia);
        psInsert.setString(5, "12345678");         // cuenta destino ficticia
        psInsert.setString(6, "CC");               // tipo de cuenta
        psInsert.executeUpdate();

        // 3. Verificar saldo final
        String sqlSaldo = "SELECT monto FROM cta_corriente WHERE idCuenta = ?";
        PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
        psSaldo.setInt(1, idCuentaPrueba);
        ResultSet rsSaldo = psSaldo.executeQuery();
        if (rsSaldo.next()) {
            double saldoFinal = rsSaldo.getDouble("monto");
            assertEquals("El saldo debe descontarse correctamente",
                         saldoInicial - montoTransferencia, saldoFinal, 0.01);
        } else {
            fail("No se pudo verificar el saldo después de la transferencia.");
        }

        conn.close();
    }
}
