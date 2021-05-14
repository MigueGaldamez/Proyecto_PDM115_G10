package com.example.proyecto_pdm_g10;

public class Capacitador {
    private String idCapacitador;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String idEntidadCapacitadora;
    private String correo;
    private String profesion;


    public Capacitador() {
    }

    public Capacitador(String idCapacitador, String nombres, String apellidos) {
        this.idCapacitador = idCapacitador;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Capacitador(String idCapacitador, String nombres, String apellidos, String telefono, String idEntidadCapacitadora, String correo, String profesion) {
        this.idCapacitador = idCapacitador;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.idEntidadCapacitadora = idEntidadCapacitadora;
        this.correo = correo;
        this.profesion = profesion;
    }

    public String getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(String idCapacitador) {
        this.idCapacitador = idCapacitador;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdEntidadCapacitadora() {
        return idEntidadCapacitadora;
    }

    public void setIdEntidadCapacitadora(String idEntidadCapacitadora) {
        this.idEntidadCapacitadora = idEntidadCapacitadora;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
