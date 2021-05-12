package com.example.proyecto_pdm_g10;

public class Ubicacion
{
    private String idUbicacion;
    private int idFacultad;
    private String nombre;
    public Ubicacion()
    {
    }
    public Ubicacion(String idUbicacion, int idFacultad, String nombre)
    {
        this.idUbicacion = idUbicacion;
        this.idFacultad = idFacultad;
        this.nombre = nombre;
    }
    public  String getIdUbicacion()
    {
        return idUbicacion;
    }
    public void setIdUbicacion(String idUbicacion)
    {
        this.idUbicacion=idUbicacion;
    }
    public int getIdFacultad()
    {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad)
    {
        this.idFacultad = idFacultad;
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
