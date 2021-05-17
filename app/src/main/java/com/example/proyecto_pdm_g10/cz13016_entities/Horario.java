package com.example.proyecto_pdm_g10.cz13016_entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Horario {
    //idHorario INTEGER NOT NULL PRIMARY KEY,horaInicio TIME, horaFin TIME, idCapacitacion INTEGER, idDia VARCHAR(7)
    private Integer idHorario;
    private String horaInicio;
    private String horaFin;
    private Integer idCapacitacion;
    private String idDia;

    public Horario() {
    }

    public Horario(Integer idHorario, String horaInicio, String horaFin, Integer idCapacitacion, String idDia) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idCapacitacion = idCapacitacion;
        this.idDia = idDia;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getIdCapacitacion() {
        return idCapacitacion;
    }

    public void setIdCapacitacion(Integer idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }

    public String getIdDia() {
        return idDia;
    }

    public void setIdDia(String idDia) {
        this.idDia = idDia;
    }
}
