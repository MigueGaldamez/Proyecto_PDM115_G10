package com.example.proyecto_pdm_g10.cz13016_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.Dia;
import com.example.proyecto_pdm_g10.Local;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;
import com.example.proyecto_pdm_g10.cz13016_entities.Horario;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HorarioCrud {

    private Context context;
    private ControlBDProyecto control;
    private SQLiteDatabase db;
    private ControlBDProyecto.DatabaseHelper DBHelper;

    public HorarioCrud(Context context) {
        this.context = context;
        control = new ControlBDProyecto(context);
        DBHelper = control.getDBHelper();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }
//***************************************************************

    public String insertarHorario(Horario horario){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues horarioContent = new ContentValues();

//                db.execSQL("CREATE TABLE horario(idHorario INTEGER NOT NULL PRIMARY KEY,horaInicio TIME, horaFin TIME, idCapacitacion INTEGER, idDia VARCHAR(7))");

        horarioContent.put("idHorario", horario.getIdHorario());
        horarioContent.put("horaInicio", horario.getHoraInicio());
        horarioContent.put("horaFin", horario.getHoraFin());
        horarioContent.put("idCapacitacion", horario.getIdCapacitacion());
        horarioContent.put("idDia", horario.getIdDia());

        contador=db.insert("horario", null, horarioContent);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }

    public List<Dia> allLDia(){
        ArrayList<Dia> listDia = new ArrayList<>();

        Cursor cursor= db.rawQuery("select * from dia",null);
        if (cursor.moveToFirst()){
            do {
                Dia  dia = new Dia(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                listDia.add(dia);
            }while (cursor.moveToNext());
        }
        return listDia;
    }

    public Horario extraerHorario(String idHorar){
//                db.execSQL("CREATE TABLE horario(idHorario INTEGER NOT NULL PRIMARY KEY,horaInicio CHAR(5), horaFin CHAR(5), idCapacitacion INTEGER, idDia VARCHAR(7))");
        Horario  horario;

        Cursor cursor = db.rawQuery("SELECT * FROM horario WHERE idHorario = '"+idHorar+"'",null);
        if(cursor.moveToFirst()){
            horario = new Horario(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3), cursor.getString(4));
            return horario;

        }
        return null;

    }

    public Integer extraerHorarioPosition(String idDia){

        Integer position = 0;

        Cursor cursor= db.rawQuery("select * from dia",null);
        if (cursor.moveToFirst()){
            do {
                Dia  dia = new Dia(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                if (idDia.equals(dia.getIdDia())){
                    return position;
                }
                position++;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public boolean buscarHorario(String idHorario){
        Cursor cursor = db.rawQuery("SELECT * FROM horario WHERE idHorario = '"+idHorario+"'",null);
        if(cursor.moveToFirst())
            return  true;

        return false;

    }

    public String actualizarHorario(Horario horario){
        if(buscarHorario(horario.getIdHorario())){

            ContentValues hrio = new ContentValues();

            hrio.put("horaInicio", horario.getHoraInicio());
            hrio.put("horaFin", horario.getHoraFin());
            hrio.put("idCapacitacion", horario.getIdCapacitacion());
            hrio.put("idDia", horario.getIdDia());

            db.update("horario", hrio, "idHorario='"+horario.getIdHorario()+"'", null);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + horario.getIdHorario()+ " no existe";
        }
    }

    public String eliminarHorario(String idHrio){
        String regAfectados;
        int contador=0;
        if (buscarHorario(idHrio)) {
            contador+=db.delete("horario", "idHorario='"+idHrio+"'", null);
            regAfectados = "Eliminado con exito";
        }else
            regAfectados = "No se encontro ningun registro";

        return  regAfectados;
    }

}
