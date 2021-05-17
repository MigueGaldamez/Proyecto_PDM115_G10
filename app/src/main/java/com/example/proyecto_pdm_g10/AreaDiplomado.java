package com.example.proyecto_pdm_g10;

public class AreaDiplomado {
    private String idAreaDiplomado;
    private String nombre;
    private String descripcion;
    private String idDiplomado;



    public AreaDiplomado() {
    }

    public AreaDiplomado(String idAreaDiplomado, String nombre) {
        this.idAreaDiplomado = idAreaDiplomado;
        this.nombre = nombre;
    }

    public AreaDiplomado(String idAreaDiplomado, String nombre, String descripcion, String idDiplomado) {
        this.idAreaDiplomado = idAreaDiplomado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idDiplomado = idDiplomado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdAreaDiplomado() {
        return idAreaDiplomado;
    }

    public void setIdAreaDiplomado(String idAreaDiplomado) {
        this.idAreaDiplomado = idAreaDiplomado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getIdDiplomado() {
        return idDiplomado;
    }

    public void setIdDiplomado(String idDiplomado) {
        this.idDiplomado = idDiplomado;
    }
}
