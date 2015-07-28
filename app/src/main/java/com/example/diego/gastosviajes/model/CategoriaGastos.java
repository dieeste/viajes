package com.example.diego.gastosviajes.model;


public enum CategoriaGastos {

    ALOJAMIENTO("ALOJAMIENTO"), TRANSPORTE("TRANSPORTE"),
    FIESTA("FIESTA"), COMIDA("COMIDA"), OTROS("OTROS");

    private String nombreCategoria;

    private CategoriaGastos(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }


}
