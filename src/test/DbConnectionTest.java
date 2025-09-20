package com.platinum.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

/**
 * Prueba unitaria para validar conexión a la base de datos MySQL.
 */
public class DbConnectionTest {

    // Ajusta estos valores según tu instalación
	// Configuración BD (ajusta usuario y password)
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/cuentas_cliente?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "mc_user";     
    private static final String JDBC_PASS = "mc_pass_123";

    @Test
    public void testConexionBD() {
        try {
            // Cargar driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Intentar conectar
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Validar que la conexión no sea nula
            assertNotNull("La conexión debe ser válida", conn);

            System.out.println("Conexión a la BD establecida correctamente.");
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
