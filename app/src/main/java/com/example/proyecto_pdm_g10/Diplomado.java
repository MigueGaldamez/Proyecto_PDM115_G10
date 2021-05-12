package com.example.proyecto_pdm_g10;

public class Diplomado {
    private String idDiplomado;
    private String titulo;
    private String descripcion;
    private String capacidades;



    public Diplomado() {
    }

    public Diplomado(String idDiplomado, String titulo, String descripcion, String capacidades) {
        this.idDiplomado = idDiplomado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.capacidades = capacidades;
    }

    public String getIdDiplomado() {
        return idDiplomado;
    }

    public void setIdDiplomado(String idDiplomado) {
        this.idDiplomado = idDiplomado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(String capacidades) {
        this.capacidades = capacidades;
    }
}
