package com.tiendamotos.servicio;

import com.tiendamotos.dao.MotoDAO;
import com.tiendamotos.modelo.Moto;

import java.util.List;

public class MotoService {

    private final MotoDAO motoDAO;

    public MotoService() {

        motoDAO = new MotoDAO();
    }

    public void registrarMoto(Moto moto) {

        if (moto.getMarca() == null ||
                moto.getMarca().isBlank()) {

            System.out.println(

                    "La marca es obligatoria!"
            );

            return;
        }


        if (moto.getModelo() == null ||
                moto.getModelo().isBlank()) {
            System.out.println(
                    "el modelo es obligatorio!"
            );

            return;
        }

        if (moto.getPrecio() <= 0) {

            System.out.println(
                    "el precio debe ser mayor que cero!"
            );

            return;
        }

        if (moto.getStock() < 0) {
            System.out.println(
                    "el STOCK no puede ser negativo!"
            );

            return;
        }

        motoDAO.insertar(moto);
    }

    public List<Moto> listarMotos() {

        return motoDAO.listar();
    }

    public void actualizarMoto(Moto moto) {

        motoDAO.actualizar(moto);
    }

    public void eliminarMoto(int idMoto) {
        motoDAO.eliminar(idMoto);
    }   
}
