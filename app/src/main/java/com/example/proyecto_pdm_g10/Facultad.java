package com.example.proyecto_pdm_g10;

public class Facultad
{
    private String idFacultad;
    private String nombre;
    public Facultad()
    {
    }
    public Facultad(String idFacultad, String nombre)
    {
        this.idFacultad = idFacultad;
        this.nombre = nombre;
    }
    public  String getIdFacultad()
    {
        return idFacultad;
    }
    public void setIdFacultad(String idFacultad)
    {
        this.idFacultad=idFacultad;
    }
    public  String getnombre()
    {
        return nombre;
    }
    public void setnombre(String nombre)
    {
        this.nombre=nombre;
    }
}
