package com.example.proyecto_pdm_g10;

public class TipoUbicacion
{
    private String id;
    private String nombre;
    public  TipoUbicacion()
    {
    }
    public TipoUbicacion(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    public String getId()
    {
        return id;
    }
    public String getNombre()
    {
        return nombre;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
