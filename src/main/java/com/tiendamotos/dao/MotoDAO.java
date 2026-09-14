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

    public List<Moto> listar() {

        List<Moto> motos = new ArrayList<>();

        String sql = """
                SELECT
                    ID_MOTO,
                    MARCA,
                    MODELO,
                    CILINDRADA,
                    ANIO,
                    PRECIO,
                    STOCK
                FROM MOTOS
                ORDER BY ID_MOTO
                """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Moto moto = new Moto();

                moto.setIdMoto(
                        result.getInt("ID_MOTO")
                );

                moto.setMarca(
                        result.getString("MARCA")
                );

                moto.setModelo(
                        result.getString("MODELO")
                );

                moto.setCilindrada(
                        result.getInt("CILINDRADA")
                );

                moto.setAnio(
                        result.getInt("ANIO")
                );

                moto.setPrecio(
                        result.getDouble("PRECIO")
                );

                moto.setStock(
                        result.getInt("STOCK")
                );

                motos.add(moto);
            }
        } catch (SQLException e) {

            System.out.println(
                    "error consultando MOTOS: "
                    + e.getMessage()
            );
        }

        return motos;
    }   // listar .

    public void actualizar(Moto moto) {

        String sql = """
                UPDATE MOTOS
                SET
                    MARCA = ?,
                    MODELO = ?,
                    CILINDRADA = ?,
                    ANIO = ?,
                    PRECIO = ?,
                    STOCK = ?,
                WHERE ID_MOTO = ?
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
            statement.setInt(7, moto.getIdMoto());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println(
                        "MOTO actualizada correctamente!"
                );
            } else {

                System.out.println(
                        "no se encontro la moto!"
                );
            }
        } catch (SQLException e) {

            System.out.println(
                    "error actualizando moto: "
                    + e.getMessage()
            );
        }
    }

    public void eliminar(int idMoto) {

        String sql =
                "DELETE FROM MOTOS WHERE ID_MOTO = ?";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, idMoto);

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println(
                        "MOTO elimnada correctamente!"
                );
            } else {

                System.out.println(
                        "NO se encontro la moto!"
                );
            }
        } catch (SQLException e) {

            System.out.println(
                "error eliminando MOTO: "
                    + e.getMessage()
            );
        }
    }
}
