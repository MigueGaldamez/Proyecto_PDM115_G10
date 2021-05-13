package com.example.proyecto_pdm_g10;

import java.time.LocalDate;
import java.util.Date;

public class Solicitud {
    private String idSolicitud;
    private  String fechaSolicitud;
    private String estadoSolicitud;
    private String capacitacionId;




    public Solicitud() {
    }

    public Solicitud(String idSolicitud, String fechaSolicitud, String estadoSolicitud, String  capacitacionId ) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.estadoSolicitud = estadoSolicitud;
        this.capacitacionId= capacitacionId;

    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) { this.fechaSolicitud = fechaSolicitud;}

    public String getEstadoSolicitud() { return estadoSolicitud; }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud =  estadoSolicitud;
    }

    public String getCapacitacionId() {
        return capacitacionId;
    }

    public void setCapacitacionId(String capacitacionId) {
        this.capacitacionId =  capacitacionId;
    }

}
