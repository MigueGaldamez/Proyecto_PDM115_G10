package com.example.proyecto_pdm_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBDProyecto {

    private static final String[]camposAreaInteres = new String [] {"codigo","nombre","descripcion"};
    private static final String[]camposEntidadCapacitadora = new String []   {"codigo", "nombre", "descripcion", "telefono", "correo"};

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControlBDProyecto(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "capacitacion.s3db";
        private static final int VERSION = 1;
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE areaInteres(codigo VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100));");
                db.execSQL("CREATE TABLE entidadCapacitadora(codigo VARCHAR(6) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100),telefono VARCHAR(20),correo VARCHAR(100));");

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }

    }
    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }
    public String insertar(AreaInteres areaInteres){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues area = new ContentValues();
        area.put("codigo", areaInteres.getCodigo());
        area.put("nombre", areaInteres.getNombre());
        area.put("descripcion", areaInteres.getDescripcion());

        contador=db.insert("areaInteres", null, area);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }
    public String actualizar(AreaInteres areaInteres){
        if(verificarIntegridad(areaInteres, 1)){
            String[] id = {areaInteres.getCodigo()};
            ContentValues cv = new ContentValues();
            cv.put("nombre", areaInteres.getNombre());
            cv.put("descripcion", areaInteres.getDescripcion());

            db.update("areaInteres", cv, "codigo = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + areaInteres.getCodigo() + " no existe";
        }
    }
    public String eliminar(AreaInteres areaInteres){
        String regAfectados="filas afectadas= ";
        int contador=0;
        //Verificar si se encuentra en otra tabla
        /*if (verificarIntegridad(areaInteres,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/
        contador+=db.delete("areaInteres", "codigo='"+areaInteres.getCodigo()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public AreaInteres consultarAreaInteres(String codigo){
        String[] id = {codigo};
        Cursor cursor = db.query("areaInteres", camposAreaInteres, "codigo = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            AreaInteres areaInteres = new AreaInteres();
            areaInteres.setCodigo(cursor.getString(0));
            areaInteres.setNombre(cursor.getString(1));
            areaInteres.setDescripcion(cursor.getString(2));

            return areaInteres;
        }else{
            return null;
        }
    }
    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException {
        switch (relacion) {
            //Verificando si existe el area de interes antes de actualizarlo
            case 1:
            {
                AreaInteres areaInteres = (AreaInteres) dato;
                String[] id = {areaInteres.getCodigo()};
                abrir();
                Cursor c2 = db.query("areaInteres", null, "codigo = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            default:
                return false;
        }

    }


    public String llenarBDCarnet() {
        cerrar();
        return "Guardo Correctamente";
    }

}