package com.example.proyecto_pdm_g10.cz13016_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;


public class CapacitacionCrud {

   private Context context;
   private ControlBDProyecto control;
   private SQLiteDatabase db;
    private ControlBDProyecto.DatabaseHelper DBHelper;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CapacitacionCrud(Context context) {
        this.context = context;
        control = new ControlBDProyecto(context);
        DBHelper = control.getDBHelper();
    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }

    public String insertarCapacitacion(Capacitacion capacitacion){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues capaci = new ContentValues();

       // db.execSQL("CREATE TABLE capacitacion(idCapacitacion INTEGER NOT NULL PRIMARY KEY autoincrement,descripcion VARCHAR(100),precio REAL,idLocal INTEGER, idAreasDip INTEGER, idAreaIn INTEGER, idCapacitador INTEGER);");

        capaci.put("idCapacitacion",capacitacion.getIdCapacitacion());
        capaci.put("descripcion", capacitacion.getDescrip());
        capaci.put("precio", capacitacion.getPrecio());
        capaci.put("idLocal", capacitacion.getIdLocal());
        capaci.put("idAreasDip", capacitacion.getIdAreaDip());
        capaci.put("idAreaIn", capacitacion.getIdAreaIn());
        capaci.put("idCapacitador", capacitacion.getIdCapacitador());

        contador=db.insert("capacitacion", null, capaci);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }
}
