package com.tiendamotos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    //

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO =
            "system";

    private static final String PASSWORD =
            "Tapiero123";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }

    public static void cerrar(Connection connection)
    {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {

                System.out.println(
                        "error cerrando conexion: " +
                                e.getMessage()
                );
            }
        }
    }   
}
