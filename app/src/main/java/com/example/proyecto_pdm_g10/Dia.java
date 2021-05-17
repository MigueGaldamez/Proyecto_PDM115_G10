package com.example.proyecto_pdm_g10;

public class Dia {
    private String idDia;
    private String nomDia;
    private String fecha;

    public Dia() {
    }

    public Dia(String idDia, String nomDia, String fecha) {
        this.idDia = idDia;
        this.nomDia = nomDia;
        this.fecha = fecha;
    }

    public String getIdDia() {
        return idDia;
    }

    public void setIdDia(String idDia) {
        this.idDia = idDia;
    }

    public String getNomDia() {
        return nomDia;
    }

    public void setNomDia(String nomDia) {
        this.nomDia = nomDia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
