package com.example.proyecto_pdm_g10.cz13016_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_pdm_g10.Diplomado;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.Capacitador;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.Local;


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

    public List<Local> allLoca(){
        ArrayList<Local> lisLocal = new ArrayList<>();

//Obtne el listado de areas
        Cursor cursor= db.rawQuery("select * from local",null);
        if (cursor.moveToFirst()){
            do {
                Local  local = new Local(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                lisLocal.add(local);
            }while (cursor.moveToNext());
        }
        return lisLocal;
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

    public List<AreaDiplomado> allAreaDip(){
        ArrayList<AreaDiplomado> listAreaDip = new ArrayList<>();
//                db.execSQL("CREATE TABLE areaDiplomado(idAreaDiplomado CHAR(5) NOT NULL PRIMARY KEY,nombre VARCHAR(20),descripcion VARCHAR(100),idDiplomado VARCHAR(5));");
//Obtne el listado de areas
        Cursor cursor= db.rawQuery("select * from areaDiplomado",null);
        if (cursor.moveToFirst()){
            do {
                AreaDiplomado  areaDip = new AreaDiplomado(cursor.getString(0),cursor.getString(1));
                listAreaDip.add(areaDip);
            }while (cursor.moveToNext());
        }
        return listAreaDip;
    }

    public List<Capacitador> allCapacitador(){
        ArrayList<Capacitador> listCapacitador = new ArrayList<>();

//Obtne el listado de areas
        Cursor cursor= db.rawQuery("select * from capacitador",null);
        if (cursor.moveToFirst()){
            do {
                Capacitador  capacitador = new Capacitador(cursor.getString(0),cursor.getString(1), cursor.getString(2));
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

    public Capacitacion extraerCapacitacion(Integer idCapacitacion){

        Capacitacion  capacitacion;

        Cursor cursor = db.rawQuery("SELECT * FROM capacitacion WHERE idCapacitacion = '"+idCapacitacion+"'",null);
        if(cursor.moveToFirst()){
            capacitacion = new Capacitacion(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            return capacitacion;

        }
        return null;

    }

    public boolean buscarCapacitacion(Integer idCapacitacion){
        Cursor cursor = db.rawQuery("SELECT * FROM capacitacion WHERE idCapacitacion = '"+idCapacitacion+"'",null);
        if(cursor.moveToFirst())
            return  true;

        return false;

    }

    public String actualizarCapacitacion(Capacitacion capacitacion){
        if(buscarCapacitacion(capacitacion.getIdCapacitacion())){

            ContentValues capaci = new ContentValues();

            capaci.put("descripcion", capacitacion.getDescrip());
            capaci.put("precio", capacitacion.getPrecio());
            capaci.put("idLocal", capacitacion.getIdLocal());
            capaci.put("idAreasDip", capacitacion.getIdAreaDip());
            capaci.put("idAreaIn", capacitacion.getIdAreaIn());
            capaci.put("idCapacitador", capacitacion.getIdCapacitador());

            db.update("capacitacion", capaci, "idCapacitacion='"+capacitacion.getIdCapacitacion()+"'", null);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + capacitacion.getIdCapacitacion() + " no existe";
        }
    }



    //**************************************************************************************************************
    public String insertarLocal(Local local){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues localContent = new ContentValues();

//      db.execSQL("CREATE TABLE local(id VARCHAR(7) NOT NULL ,idUbicacion VARCHAR(7) NOT NULL, idTipoUbicacion VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idUbicacion, idTipoUbicacion));");
        localContent.put("id", local.getIdLocal());
        localContent.put("idUbicacion", local.getIdUbicacion());
        localContent.put("idTipoUbicacion", local.getIdTipoUbicacion());
        localContent.put("nombre", local.getNombre());

        contador=db.insert("local", null, localContent);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }

    public Local extraerLocal(String primaryKey){

        Local localRetorno;

        // SELECT * FROM tabla WHERE nombre||apellido='Alonso'
        Cursor cursor = db.rawQuery("SELECT * FROM local WHERE id || idUbicacion || idTipoUbicacion = '"+primaryKey+"'",null);
        if(cursor.moveToFirst()){
            localRetorno = new Local(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3));
            return localRetorno;

        }
        return null;

    }


}

