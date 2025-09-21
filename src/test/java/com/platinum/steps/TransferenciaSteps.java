package com.platinum.steps;

import io.cucumber.java.es.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class TransferenciaSteps {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";
    private static final String JDBC_PASS = "mc_pass_123";

    private int idCuentaPrueba;
    private double saldoInicial;
    private double montoTransferencia;
    private double saldoFinal;

    @Dado("que la cuenta con id {int} tiene un saldo disponible")
    public void que_la_cuenta_con_id_tiene_un_saldo_disponible(Integer idCuenta) throws Exception {
        idCuentaPrueba = idCuenta;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            PreparedStatement ps = conn.prepareStatement("SELECT monto FROM cta_corriente WHERE id_cuenta = ?");
            ps.setInt(1, idCuentaPrueba);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                saldoInicial = rs.getDouble("monto");
            } else {
                fail("La cuenta con id " + idCuentaPrueba + " no existe en la BD.");
            }
        }
    }

    @Cuando("se realiza una transferencia de {double} pesos")
    public void se_realiza_una_transferencia_de_pesos(Double monto) throws Exception {
        montoTransferencia = monto;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            // Descontar saldo
            PreparedStatement psUpdate = conn.prepareStatement(
                "UPDATE cta_corriente SET monto = monto - ? WHERE id_cuenta = ?"
            );
            psUpdate.setDouble(1, montoTransferencia);
            psUpdate.setInt(2, idCuentaPrueba);
            int filas = psUpdate.executeUpdate();
            assertEquals(1, filas, "Debe afectar exactamente 1 fila");

            // Registrar transacción
            PreparedStatement psInsert = conn.prepareStatement(
                "INSERT INTO transaccion (rut_cliente, rut_dueno, id_cuenta, monto_transferencia, cuenta_transferencia, tipo_cuenta) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            );
            psInsert.setString(1, "22222222-2"); // rut cliente prueba
            psInsert.setString(2, "33333333-3"); // rut destino ficticio
            psInsert.setInt(3, idCuentaPrueba);
            psInsert.setDouble(4, montoTransferencia);
            psInsert.setString(5, "12345678");   // cuenta destino ficticia
            psInsert.setString(6, "CC");
            psInsert.executeUpdate();
        }
    }

    @Entonces("el saldo final debe ser menor en {double} pesos")
    public void el_saldo_final_debe_ser_menor_en_pesos(Double montoEsperado) throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            PreparedStatement ps = conn.prepareStatement("SELECT monto FROM cta_corriente WHERE id_cuenta = ?");
            ps.setInt(1, idCuentaPrueba);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                saldoFinal = rs.getDouble("monto");
                assertEquals(saldoInicial - montoEsperado, saldoFinal, 0.01,
                        "El saldo no coincide después de la transferencia");
            } else {
                fail("No se encontró la cuenta para verificar el saldo.");
            }
        }
    }
}
