package com.platinum.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

public class DbConnectionTest {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_clientes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";     
    private static final String JDBC_PASS = "mc_pass_123";

    @Test
    public void testConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            assertNotNull(conn, "La conexión debe ser válida");
            System.out.println("Conexión a la BD establecida correctamente.");
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
