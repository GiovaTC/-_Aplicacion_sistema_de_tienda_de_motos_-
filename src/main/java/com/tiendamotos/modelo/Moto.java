package com.tiendamotos.modelo;

public class Moto {

    private int idMoto;
    private String marca;
    private String modelo;
    private int cilindrada;
    private int anio;
    private double precio;
    private int stock;

    public Moto() {
        // vacio .
    }

    public Moto(int idMoto, String marca, String modelo,
                int cilindrada, int anio, double precio, int stock) {

        this.idMoto = idMoto;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.anio = anio;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {

        return "Moto{" +
                "idMoto=" + idMoto +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cilindrada=" + cilindrada +
                ", anio=" + anio +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}   
