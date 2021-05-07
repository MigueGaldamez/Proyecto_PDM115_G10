package com.example.proyecto_pdm_g10;

public class OpcionCrud {
    private String idOpcion;
    private String desOpcion;
    private int numCrud;

    public OpcionCrud() {
    }

    public OpcionCrud(String idOpcion, String desOpcion, int numCrud) {
        this.idOpcion = idOpcion;
        this.desOpcion = desOpcion;
        this.numCrud = numCrud;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDesOpcion() {
        return desOpcion;
    }

    public void setDesOpcion(String desOpcion) {
        this.desOpcion = desOpcion;
    }

    public int getNumCrud() {
        return numCrud;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }
}
