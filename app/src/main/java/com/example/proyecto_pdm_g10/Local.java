package com.example.proyecto_pdm_g10;

public class Local
{
    private String idLocal;
    private String idUbicacion;
    private String idTipoUbicacion;
    private String nombre;

    public Local()
    {
    }


    public Local(String idLocal, String idUbicacion, String idTipoUbicacion, String nombre)
    {
        this.idLocal = idLocal;
        this.idUbicacion = idUbicacion;
        this.idTipoUbicacion = idTipoUbicacion;
        this.nombre = nombre;
    }

    public String getIdLocal()
    {
        return idLocal;
    }

    public String getIdUbicacion()
    {
        return idUbicacion;
    }

    public String getIdTipoUbicacion()
    {
        return idTipoUbicacion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setIdLocal(String idLocal)
    {
        this.idLocal = idLocal;
    }

    public void setIdUbicacion(String idUbicacion)
    {
        this.idUbicacion = idUbicacion;
    }

    public void setIdTipoUbicacion(String idTipoUbicacion)
    {
        this.idTipoUbicacion = idTipoUbicacion;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
