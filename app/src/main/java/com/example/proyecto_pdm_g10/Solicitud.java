package com.example.proyecto_pdm_g10;

import java.time.LocalDate;
import java.util.Date;

public class Solicitud {
    private String idSolicitud;
    private Date fechaSolicitud;
    private Boolean estado;




    public Solicitud() {
    }

    public Solicitud(String idSolicitud, Date fechaSolicitud, Boolean estado ) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;

    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idDiplomado) {
        this.idSolicitud = idDiplomado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud;}

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado =  estado;
    }


}
