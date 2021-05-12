package com.example.proyecto_pdm_g10;

public class Ubicacion
{
    private String idUbicacion;
    private String idFacultad;
    private String nombre;
    public Ubicacion()
    {
    }
    public Ubicacion(String idUbicacion, String idFacultad, String nombre)
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
    public String getIdFacultad()
    {
        return idFacultad;
    }

    public void setIdFacultad(String idFacultad)
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
