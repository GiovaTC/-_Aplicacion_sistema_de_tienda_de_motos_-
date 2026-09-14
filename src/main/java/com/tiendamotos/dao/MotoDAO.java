package com.tiendamotos.dao;

import com.tiendamotos.config.ConexionBD;
import com.tiendamotos.modelo.Moto;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {

    public void insertar(Moto moto) {

        String sql = """
                INSERT INTO MOTOS
                (
                    ID_MOTO,
                    MARCA,
                    MODELO,
                    CILINDRADA,
                    ANIO,
                    PRECIO,
                    STOCK
                )
                VALUES
                (
                    SEQ_MOTOS.NEXTVAL,
                    ?, ?, ?, ?, ?, ?
                )
                """;
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, moto.getMarca());
            statement.setString(2, moto.getModelo());
            statement.setInt(3, moto.getCilindrada());
            statement.setInt(4, moto.getAnio());
            statement.setDouble(5, moto.getPrecio());
            statement.setInt(6, moto.getStock());

            statement.executeUpdate();

            System.out.println(
                    "Moto REGISTRADA correctamente!"
            );
        } catch (SQLException e) {

            System.out.println(
                    "Error registrando moto: "
                    + e.getMessage()
            );
        }
    }
}
