package com.example.proyecto_pdm_g10.cz13016_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.Capacitador;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;

import java.util.ArrayList;
import java.util.List;


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

    public List<AreaInteres> allAreasInteres(){
        ArrayList<AreaInteres> listAreaIn = new ArrayList<>();

//Obtne el listado de areas
        Cursor cursor= db.rawQuery("select * from areaInteres",null);
        if (cursor.moveToFirst()){
            do {
                AreaInteres  areaInteres = new AreaInteres(cursor.getString(0),cursor.getString(1));
                listAreaIn.add(areaInteres);
            }while (cursor.moveToNext());
        }
        return listAreaIn;
    }

    public List<Capacitador> allCapacitador(){
        ArrayList<Capacitador> listCapacitador = new ArrayList<>();

//Obtne el listado de areas
        Cursor cursor= db.rawQuery("select * from capacitador",null);
        if (cursor.moveToFirst()){
            do {
                Capacitador  capacitador = new Capacitador(cursor.getString(0),cursor.getString(1));
                listCapacitador.add(capacitador);
            }while (cursor.moveToNext());
        }
        return listCapacitador;
    }

    public String eliminarCapacitacion(Integer idCap){
        String regAfectados;
        int contador=0;
        if (buscarCapacitacion(idCap)) {
            contador+=db.delete("capacitacion", "idCapacitacion='"+idCap+"'", null);
            regAfectados = "Eliminado con exito";
        }else
            regAfectados = "No se encontro ningun registro";

        return  regAfectados;
    }

    public boolean buscarCapacitacion(Integer idCapacitacion){
        Cursor cursor = db.rawQuery("SELECT * FROM capacitacion WHERE idCapacitacion = '"+idCapacitacion+"'",null);
        if(cursor.moveToFirst())
          return  true;

        return false;

    }

}
