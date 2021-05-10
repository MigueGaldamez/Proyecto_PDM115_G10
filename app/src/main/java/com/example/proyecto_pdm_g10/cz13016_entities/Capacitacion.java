package com.example.proyecto_pdm_g10.cz13016_entities;

public class Capacitacion {
    // db.execSQL("CREATE TABLE capacitacion(idCapacitacion ,descripcion VARCHAR(100),precio REAL,idLocal INTEGER, idAreasDip INTEGER, idAreaIn INTEGER, idCapacitador INTEGER);");
private Integer idCapacitacion;
private String descrip;
private float precio;
private Integer idLocal;
private Integer idAreaDip;
private Integer idAreaIn;
private Integer idCapacitador;

    public Capacitacion() {

    }

    public Capacitacion(Integer idCapacitacion, String descrip, float precio, Integer idLocal, Integer idAreaDip, Integer idAreaIn, Integer idCapacitador) {
        this.idCapacitacion = idCapacitacion;
        this.descrip = descrip;
        this.precio = precio;
        this.idLocal = idLocal;
        this.idAreaDip = idAreaDip;
        this.idAreaIn = idAreaIn;
        this.idCapacitador = idCapacitador;
    }

    public Integer getIdCapacitacion() {
        return idCapacitacion;
    }

    public void setIdCapacitacion(Integer idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public Integer getIdAreaDip() {
        return idAreaDip;
    }

    public void setIdAreaDip(Integer idAreaDip) {
        this.idAreaDip = idAreaDip;
    }

    public Integer getIdAreaIn() {
        return idAreaIn;
    }

    public void setIdAreaIn(Integer idAreaIn) {
        this.idAreaIn = idAreaIn;
    }

    public Integer getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(Integer idCapacitador) {
        this.idCapacitador = idCapacitador;
    }
}
