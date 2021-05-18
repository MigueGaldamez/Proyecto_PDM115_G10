package com.example.proyecto_pdm_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class ControlBDProyecto {
    //PARA CADA TABLA HAY QUE LISTAR LOS CAMPOS
    private static final String[]camposAreaInteres = new String [] {"codigo","nombre","descripcion"};
    private static final String[]camposEntidadCapacitadora = new String []   {"codigo", "nombre", "descripcion", "telefono", "correo","tipo"};
    private static final String[]camposOpcionCrud = new String [] {"idOpcion","desOpcion","numCrud"};
    private static final String[]camposUsuario = new String [] {"idUsuario","nomUsuario","clave"};
    private static final String[]camposAccesoUsuario = new String [] {"idOpcion","idUsuario"};
    //________________________z____________________________________________________________________________________________________________________________________________________________________________________________
    private static final String[]camposFacultad = new String [] {"id","nombre"};
    private static final String[]camposUbicacion2 = new String[] {"u.id","f.nombre"," u.nombre"};
    private static final String[]camposTipoUbicacion = new String[]{"id","nombre"};
    private static final String[]camposLocal1 = new String[]{"l.id","u.nombre","t.nombre","l.nombre"};
    //________________________________________________________________________________________________________________________________________________________________________________________________________________________
    private static final String[]camposDiplomado = new String [] {"idDiplomado","titulo","descripcion","capacidades"};
    private static final String[]camposAreaDiplomado = new String [] {"idAreaDiplomado","nombre","descripcion","idDiplomado"};
    private static final String[]camposCapacitador = new String [] {"idCapacitador","nombres","apellidos","telefono","idEntidadCapacitadora","correo","profesion"};

    private static final String[]camposEmpleado=new String[] {"idEmpleado", "nombreEmpleado","apellidoEmpleado","profesion","cargo"};
    private static final String[]camposSolicitud= new String[]{"idSolicitud","fechaSolicitud","estadoSolicitud","capacitacionId","empleadoId"};
    private static final String[]camposAsistenciaEmpleado= new String[]{"idAsistenciaEmpleado","asistencia","empleadoId","capacitacionId"};


    private static final String[]camposDia = new String[] {"idDia","nomDia","fecha"};



    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControlBDProyecto(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        setDBHelper(DBHelper);
    }

    public DatabaseHelper getDBHelper() {
        return DBHelper;
    }

    public void setDBHelper(DatabaseHelper DBHelper) {
        this.DBHelper = DBHelper;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "capacitacion.s3db";
        private static final int VERSION = 1;
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                //AQUI AGREGAMOS LAS TABLAS
                db.execSQL("CREATE TABLE areaInteres(codigo VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100));");
                db.execSQL("CREATE TABLE entidadCapacitadora(codigo VARCHAR(6) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100),telefono VARCHAR(20),correo VARCHAR(100),tipo CHAR(1));");
                db.execSQL("CREATE TABLE facultad(id VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(35));");
                db.execSQL("CREATE TABLE ubicacion(id VARCHAR(7) NOT NULL ,idFacultad VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idFacultad));");
                db.execSQL("CREATE TABLE tipoUbicacion(id VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(35));");
                db.execSQL("CREATE TABLE local(id VARCHAR(7) NOT NULL ,idUbicacion VARCHAR(7) NOT NULL, idTipoUbicacion VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idUbicacion, idTipoUbicacion));");

                db.execSQL("CREATE TABLE capacitacion(idCapacitacion INTEGER NOT NULL PRIMARY KEY,descripcion VARCHAR(100),precio REAL,idLocal VARCHAR(7), idAreasDip CHAR(5), idAreaIn VARCHAR(7), idCapacitador CHAR(5));");


                db.execSQL("CREATE TABLE usuario(idUsuario CHAR(2) NOT NULL PRIMARY KEY,nomUsuario VARCHAR(30),clave CHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion CHAR(3) NOT NULL PRIMARY KEY,desOpcion VARCHAR(30),numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario(idUsuario VARCHAR(2) NOT NULL ,idOpcion VARCHAR(3) NOT NULL  ,PRIMARY KEY(idOpcion,idUsuario));");

                db.execSQL("CREATE TABLE diplomado(idDiplomado CHAR(5) NOT NULL PRIMARY KEY,titulo VARCHAR(30),descripcion VARCHAR(100),capacidades VARCHAR(100));");
                db.execSQL("CREATE TABLE areaDiplomado(idAreaDiplomado CHAR(5) NOT NULL PRIMARY KEY,nombre VARCHAR(20),descripcion VARCHAR(100),idDiplomado VARCHAR(5));");
                db.execSQL("CREATE TABLE capacitador(idCapacitador CHAR(5) NOT NULL PRIMARY KEY,nombres VARCHAR(40),apellidos VARCHAR(40),telefono VARCHAR(20),idEntidadCapacitadora VARCHAR(6),correo VARCHAR(100),profesion VARCHAR(30));");

                db.execSQL("CREATE TABLE empleado(idEmpleado CHAR(5) NOT NULL PRIMARY KEY, nombreEmpleado VARCHAR(40),apellidoEmpleado VARCHAR(40),profesion VARCHAR(20),cargo VARCHAR(20));");
                db.execSQL("CREATE TABLE solicitud(idSolicitud CHAR(5) NOT NULL PRIMARY KEY, fechaSolicitud VARCHAR(40),estadoSolicitud VARCHAR(40),capacitacionId VARCHAR(10),empleadoId VARCHAR(10));");
                db.execSQL("CREATE TABLE asistenciaEmpleado(idAsistenciaEmpleado CHAR(5) NOT NULL PRIMARY KEY, asistencia VARCHAR(40),empleadoId VARCHAR(40),capacitacionId VARCHAR(10));");


                db.execSQL("CREATE TABLE dia(idDia CHAR(5) NOT NULL PRIMARY KEY, nomDia VARCHAR(12),fecha VARCHAR(10))");
                db.execSQL("CREATE TABLE horario(idHorario char(5) NOT NULL PRIMARY KEY,horaInicio TIME, horaFin TIME)");


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
    public ArrayList<String> listaObjeto;
    public void consultarListaObjeto(int tipo, String objeto, String[]campos)
    {
        switch (tipo)
        {
            case 1:
            {
                ArrayList<Facultad> objetoList;
                Facultad facultad = null;
                objetoList = new ArrayList<Facultad>();
                Cursor cursor = db.query(objeto,campos,null,null,null,null,null);
                while (cursor.moveToNext())
                {
                    facultad = new Facultad();
                    facultad.setIdFacultad(cursor.getString(0));
                    facultad.setnombre(cursor.getString(1));
                    objetoList.add(facultad);
                }
                listaObjeto = new ArrayList<String>();
                listaObjeto.add("Seleccione");
                for (int i=0; i<objetoList.size();i++)
                {
                    listaObjeto.add(objetoList.get(i).getIdFacultad()+" - "+objetoList.get(i).getnombre());
                }
            }
            case 2:
            {
                ArrayList<Ubicacion> objetoList;
                Ubicacion ubicacion =  null;
                objetoList = new ArrayList<Ubicacion>();
                Cursor cursor = db.query(objeto,campos,null,null,null,null,null);
                while (cursor.moveToNext())
                {
                    ubicacion = new Ubicacion();
                    ubicacion.setIdUbicacion(cursor.getString(0));
                    ubicacion.setnombre(cursor.getString(1));
                    objetoList.add(ubicacion);
                }
                listaObjeto = new ArrayList<String>();
                listaObjeto.add("Seleccione");
                for (int i=0; i<objetoList.size();i++)
                {
                    listaObjeto.add(objetoList.get(i).getIdUbicacion()+" - "+objetoList.get(i).getnombre());
                }
            }
            case 3:
            {
                ArrayList<TipoUbicacion> objetoList;
                TipoUbicacion tipoUbicacion =  null;
                objetoList = new ArrayList<TipoUbicacion>();
                Cursor cursor = db.query(objeto,campos,null,null,null,null,null);
                while (cursor.moveToNext())
                {
                    tipoUbicacion = new TipoUbicacion();
                    tipoUbicacion.setId(cursor.getString(0));
                    tipoUbicacion.setNombre(cursor.getString(1));
                    objetoList.add(tipoUbicacion);
                }
                listaObjeto = new ArrayList<String>();
                listaObjeto.add("Seleccione");
                for (int i=0; i<objetoList.size();i++)
                {
                    listaObjeto.add(objetoList.get(i).getId()+" - "+objetoList.get(i).getNombre());
                }
            }
        }
    }
    public String insertar(Facultad facultad)
    {
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues facu = new ContentValues();
        facu.put("id", facultad.getIdFacultad());
        facu.put("nombre", facultad.getnombre());
        contador=db.insert("facultad", null, facu);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Facultad consultarFacultad(String id)
    {
        String[] idFacultad = {id};
        Cursor cursor  = db.query("facultad", camposFacultad, "id = ?", idFacultad, null, null, null);
        if(cursor.moveToFirst())
        {
            Facultad facultad = new Facultad();
            facultad.setIdFacultad(cursor.getString(0));
            facultad.setnombre(cursor.getString(1));
            return facultad;
        }
        else
        {
            return null;
        }
    }
    public String actualizar(Facultad facultad)
    {
        if(verificarIntegridad2(facultad, 4))
        {
            String[] id = {facultad.getIdFacultad()};
            ContentValues cv = new ContentValues();
            cv.put("nombre", facultad.getnombre());
            db.update("facultad", cv, "id = ?", id);
            return "Registro Actualizado Correctamente";
        }else
        {
            return "Registro con id " + facultad.getIdFacultad() + " no existe";
        }
    }
    public String eliminar(Facultad facultad)
    {
        String regAfectados="filas afectadas= ";
        int contador=0;
        if (verificarIntegridad2(facultad,3))
        {
            contador+=db.delete("ubicacion", "idFacultad='"+facultad.getIdFacultad()+"'", null);
        }
        contador+=db.delete("facultad", "id='"+facultad.getIdFacultad()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public String insertar(Ubicacion ubicacion)
    {
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        if(verificarIntegridad2(ubicacion,1) && verificarIntegridad2(ubicacion,5))
        {
            ContentValues ubicaciones = new ContentValues();
            ubicaciones.put("id", ubicacion.getIdUbicacion());
            ubicaciones.put("idFacultad", ubicacion.getIdFacultad());
            ubicaciones.put("nombre", ubicacion.getnombre());
            contador=db.insert("ubicacion", null, ubicaciones);
        }
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else
        {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Ubicacion consultarUbicacion(String id)
    {
        String[] idUbicacion = {id};
        //select u.id, f.nombre, u.nombre from ubicacion u JOIN facultad f on u.idFacultad = f.id;
        Cursor cursor = db.query("ubicacion u JOIN facultad f on u.idFacultad = f.id", camposUbicacion2, "u.id = ?", idUbicacion, null, null, null);
        if(cursor.moveToFirst())
        {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setIdUbicacion(cursor.getString(0));
            ubicacion.setIdFacultad(cursor.getString(1));
            ubicacion.setnombre(cursor.getString(2));
            return ubicacion;
        }
        else
        {
            return null;
        }
    }
    public String actualizar(Ubicacion ubicacion)
    {
        if(verificarIntegridad2(ubicacion, 1) && verificarIntegridad2(ubicacion,13))
        {
            String[] id = {ubicacion.getIdUbicacion()};
            ContentValues cv = new ContentValues();
            cv.put("idFacultad", ubicacion.getIdFacultad());
            cv.put("nombre",ubicacion.getnombre());
            db.update("ubicacion", cv, "id = ?", id);
            return "Registro Actualizado Correctamente";
        }
        else
        {
            return "Registro no Existe";
        }

    }
    public String eliminar(Ubicacion ubicacion)
    {
        String regAfectados="filas afectadas= ";
        int contador=0;
        String where="id='"+ubicacion.getIdUbicacion()+"'";
        contador+=db.delete("ubicacion", where, null);
        regAfectados+=contador;
        return regAfectados;

    }
    public String insertar(TipoUbicacion tipoUbicacion)
    {
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues tipoU = new ContentValues();
        tipoU.put("id", tipoUbicacion.getId());
        tipoU.put("nombre", tipoUbicacion.getNombre());
        contador=db.insert("tipoUbicacion", null, tipoU);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public TipoUbicacion consultarTipoUbicacion(String id)
    {
        String[] idTipoUbicacion = {id};
        Cursor cursor  = db.query("tipoUbicacion", camposTipoUbicacion, "id = ?", idTipoUbicacion, null, null, null);
        if(cursor.moveToFirst())
        {
            TipoUbicacion tipoUbicacion = new TipoUbicacion();
            tipoUbicacion.setId(cursor.getString(0));
            tipoUbicacion.setNombre(cursor.getString(1));
            return tipoUbicacion;
        }
        else
        {
            return null;
        }
    }
    public String actualizar(TipoUbicacion tipoUbicacion)
    {
        if(verificarIntegridad2(tipoUbicacion, 7))
        {
            String[] id = {tipoUbicacion.getId()};
            ContentValues cv = new ContentValues();
            cv.put("nombre", tipoUbicacion.getNombre());
            db.update("tipoUbicacion", cv, "id = ?", id);
            return "Registro Actualizado Correctamente";
        }else
        {
            return "Registro con id " + tipoUbicacion.getId() + " no existe";
        }
    }
    public String eliminar(TipoUbicacion tipoUbicacion)
    {
        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad2(tipoUbicacion,8))
        {
            contador+=db.delete("local", "idTipoUbicacion='"+tipoUbicacion.getId()+"'", null);
        }
         */
        contador+=db.delete("tipoUbicacion", "id='"+tipoUbicacion.getId()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public String insertar(Local local)
    {
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        if(verificarIntegridad2(local,9) && verificarIntegridad2(local,10))
        {
            ContentValues locales = new ContentValues();
            locales.put("id", local.getIdLocal());
            locales.put("idUbicacion", local.getIdUbicacion());
            locales.put("idTipoUbicacion", local.getIdTipoUbicacion());
            locales.put("nombre", local.getNombre());
            contador=db.insert("local", null, locales);
        }
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else
        {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Local consultarLocal(String id)
    {
        // select l.id, u.nombre, t.nombre, l.nombre from local l INNER JOIN ubicacion u on l.idUbicacion = u.id INNER JOIN tipoUbicacion t on l.idTipoUbicacion= t.id;
        String[] idLocal = {id};
        Cursor cursor = db.query("local l INNER JOIN ubicacion u on l.idUbicacion = u.id INNER JOIN tipoUbicacion t on l.idTipoUbicacion= t.id", camposLocal1, "l.id = ?", idLocal, null, null, null);
        if(cursor.moveToFirst())
        {
            Local local = new Local();
            local.setIdLocal(cursor.getString(0));
            local.setIdUbicacion(cursor.getString(1));
            local.setIdTipoUbicacion(cursor.getString(2));
            local.setNombre(cursor.getString(3));
            return local;
        }
        else
        {
            return null;
        }
    }
    public String actualizar(Local local)
    {
        if(verificarIntegridad2(local, 9 ) && verificarIntegridad2(local,11))
        {
            String[] id = {local.getIdLocal()};
            ContentValues cv = new ContentValues();
            cv.put("idUbicacion",local.getIdUbicacion());
            cv.put("idTipoUbicacion",local.getIdTipoUbicacion());
            cv.put("nombre",local.getNombre());
            db.update("local", cv, "id = ?", id);
            return "Registro Actualizado Correctamente";
        }
        else
        {
            return "Registro no Existe";
        }
    }
    public String eliminar(Local local)
    {
        String regAfectados="filas afectadas= ";
        int contador=0;
        String where="id='"+local.getIdLocal()+"'";
        contador+=db.delete("local", where, null);
        regAfectados+=contador;
        return regAfectados;
    }

    private boolean verificarIntegridad2(Object dato, int relacion) throws SQLException
    {
        switch(relacion)
        {
            case 1: //verificar que al insertar ubicacion exista id del facultad
            {

                Ubicacion ubicacion = (Ubicacion)dato;
                String[] id1 = {ubicacion.getIdFacultad()};
                //abrir();
                Cursor cursor1 = db.query("facultad", null, "id = ?", id1, null,
                        null, null);
                if(cursor1.moveToFirst())
                {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }
            case 2://no sirve v:
            {
                //verificar que al modificar ubicacion exista carnet del facultad, elcodigo de materia y el ciclo
                Ubicacion ubicacion1 = (Ubicacion)dato;
                String[] ids = {ubicacion1.getIdFacultad()};
                abrir();
                Cursor c = db.query("ubicacion", null, "idFacultad = ? ", ids, null, null, null);
                if(c.moveToFirst())
                {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }
            case 3:
            {
                Facultad facultad = (Facultad)dato;
                Cursor c=db.query(true, "ubicacion", new String[] {"idFacultad" }, "idFacultad='"+facultad.getIdFacultad()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 4:
            {
                //verificar que exista facultad
                Facultad facultad2 = (Facultad)dato;
                String[] id = {facultad2.getIdFacultad()};
                abrir();
                Cursor c2 = db.query("facultad", null, "id = ?", id, null, null,
                        null);
                if(c2.moveToFirst())
                {
                    //Se encontro Facultad
                    return true;
                }
                return false;
            }
            case 5://para verificar que no exista otro id de ubicacion al insertar
            {
                Ubicacion ubicacion5 = (Ubicacion)dato;
                String[] id = {ubicacion5.getIdUbicacion()};
                abrir();
                Cursor c5 = db.query("ubicacion",null,"id=?",id,null,null,null);
                if(c5.moveToFirst())
                {
                    return false;
                }
                return true;
            }
            case 6://para verificar que no exista otro id de tipoUbicacion
            {
                TipoUbicacion tipoUbicacion = (TipoUbicacion) dato;
                String[] id = {tipoUbicacion.getId()};
                abrir();
                Cursor c5 = db.query("tipoUbicacion",null,"id=?",id,null,null,null);
                if(c5.moveToFirst())
                {
                    return false;
                }
                return true;
            }
            case 7:
            {
                //verificar que exista tipoUbicacion
                TipoUbicacion tipoUbicacion2 = (TipoUbicacion)dato;
                String[] id = {tipoUbicacion2.getId()};
                abrir();
                Cursor c2 = db.query("tipoUbicacion", null, "id = ?", id, null, null,
                        null);
                if(c2.moveToFirst())
                {
                    //Se encontro TipoUbicacion
                    return true;
                }
                return false;
            }
            case 8:
            {
                TipoUbicacion tipoUbicacion3 = (TipoUbicacion) dato;
                Cursor c=db.query(true, "local", new String[] {"idTipoUbicacion" }, "idTipoUbicacion='"+tipoUbicacion3.getId()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 9:
            {
                //verificar que al insertar local exista id de la ubicacion y el id de tipoUbicacion
                Local local = (Local) dato;
                String[] id1 = {local.getIdUbicacion()};
                String[] id2 = {local.getIdTipoUbicacion()};
                //abrir();
                Cursor cursor1 = db.query("ubicacion", null, "id = ?", id1, null, null, null);
                Cursor cursor2 = db.query("tipoUbicacion", null, "id = ?", id2, null, null, null);
                if(cursor1.moveToFirst() && cursor2.moveToFirst())
                {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }
            case 10://para verificar que no exista otro id de local al insertar
            {
                Local local2 = (Local) dato;
                String[] id = {local2.getIdLocal()};
                abrir();
                Cursor c5 = db.query("local",null,"id=?",id,null,null,null);
                if(c5.moveToFirst())
                {
                    return false;
                }
                return true;
            }
            case 11://para verificar que si exista el id al actualizar
            {
                Local local2 = (Local) dato;
                String[] id = {local2.getIdLocal()};
                abrir();
                Cursor c5 = db.query("local",null,"id=?",id,null,null,null);
                if(c5.moveToFirst())
                {
                    return true;
                }
                return false;
            }
            case 12:
            {
                Ubicacion ubicacion = (Ubicacion) dato;
                Cursor c=db.query(true, "local", new String[] {"idUbicacion" }, "idUbicacion='"+ubicacion.getIdUbicacion()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 13://para verificar que si exista el id de ubicacion al insertar
            {
                Ubicacion ubicacion5 = (Ubicacion)dato;
                String[] id = {ubicacion5.getIdUbicacion()};
                abrir();
                Cursor c5 = db.query("ubicacion",null,"id=?",id,null,null,null);
                if(c5.moveToFirst())
                {
                    return true;
                }
                return false;
            }
            /*case 12:
            {
                TipoUbicacion tipoUbicacion5 = (TipoUbicacion) dato;
                Cursor c=db.query(true, "local", new String[] {"idTipoUbicacion" }, "idTipoUbicacion='"+tipoUbicacion5.getId()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }*/
            default:
                return false;
        }
    }

    //________________________________________________________________________________________________________________________________________________________________________________________________________________________



    //Insertar USUARIOS , OPCIONESCRUD y ACCESOUSUARIO y LOGIN y VERIFICACION DE PERMISOS
    public String insertar(Usuario usuario){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues usu = new ContentValues();
        usu.put("idUsuario", usuario.getIdUsuario());
        usu.put("nomUsuario", usuario.getNomUsuario());
        usu.put("clave", usuario.getClave());

        contador=db.insert("usuario", null, usu);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }

    public String insertar(OpcionCrud opcionCrud){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues opcion = new ContentValues();
        opcion.put("idOpcion", opcionCrud.getIdOpcion());
        opcion.put("desOpcion", opcionCrud.getDesOpcion());
        opcion.put("numCrud", opcionCrud.getNumCrud());

        contador=db.insert("opcionCrud", null, opcion);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar , Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }
    public String insertar(AccesoUsuario accesoUsuario){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        if(verificarIntegridad(accesoUsuario,3))
        {
            ContentValues acceso = new ContentValues();
            acceso.put("idOpcion", accesoUsuario.getIdOpcion());
            acceso.put("idUsuario", accesoUsuario.getIdUsuario());
            contador=db.insert("accesoUsuario", null, acceso);
        }
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public Usuario consultarUsuario(String idusuario, String clave){
        String[] id = {idusuario, clave};
        Cursor cursor = db.query("usuario", camposUsuario, "nomUsuario = ? AND clave = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Usuario usu = new Usuario();
            usu.setIdUsuario(cursor.getString(0));
            usu.setNomUsuario(cursor.getString(1));
            usu.setClave(cursor.getString(2));
            return usu;
        }else{
            return null;
        }
    }
    public Usuario consultarUsuario(String idusuario){
        String[] id = {idusuario};
        Cursor cursor = db.query("usuario", camposUsuario, "idUsuario = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Usuario usu = new Usuario();
            usu.setIdUsuario(cursor.getString(0));
            usu.setNomUsuario(cursor.getString(1));
            usu.setClave(cursor.getString(2));
            return usu;
        }else{
            return null;
        }
    }
    public AccesoUsuario consultarAccesoUsuario(String idusuario, String idopcion){
        String[] id = {idusuario, idopcion};
        Cursor cursor = db.query("accesoUsuario", camposAccesoUsuario, "idUsuario = ? AND idOpcion = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            AccesoUsuario acceso = new AccesoUsuario();
            acceso.setIdUsuario(cursor.getString(0));
            acceso.setIdOpcion(cursor.getString(1));
            return acceso;
        }else{
            return null;
        }
    }


    //FIN INSERTAR

    //Area de interes crud
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
            contador+=db.delete("ubicacion", "carnet='"+alumno.getCarnet()+"'", null);
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

    public List<AreaInteres> getAreaInteresList(){
        String sql = " SELECT * FROM areaInteres" ;

        List<AreaInteres> listaAreaInteres = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String codigo = cursor.getString(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                listaAreaInteres.add(new AreaInteres(codigo,nombre,descripcion));
            }while (cursor.moveToNext());
        }

        return listaAreaInteres;
    }
    //Fin crud area de interes


    //Inicio crud empleado
    public String insertar(Empleado empleado){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues emple = new ContentValues();
        emple.put("idEmpleado", empleado.getIdEmpleado());
        emple.put("nombreEmpleado", empleado.getNombreEmpleado());
        emple.put("apellidoEmpleado", empleado.getApellidoEmpleado());
        emple.put("profesion", empleado.getProfesion());
        emple.put("cargo", empleado.getCargo());
        contador=db.insert("empleado", null, emple);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Verificar inserción= "+ contador+" nombre= "+ empleado.getNombreEmpleado()+" apellido= "+ empleado.getApellidoEmpleado()+" Profesion= "+ empleado.getProfesion();

        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public Empleado consultarEmpleado(String idEmpleado){
        String[] id = {idEmpleado};
        Cursor cursor = db.query("empleado", camposEmpleado, "idEmpleado = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(cursor.getString(0));
            empleado.setNombreEmpleado(cursor.getString(1));
            empleado.setApellidoEmpleado(cursor.getString(2));
            empleado.setProfesion(cursor.getString(3));
            empleado.setCargo(cursor.getString(4));
            return empleado;
        }else{
            return null;
        }
    }
    public String actualizar(Empleado empleado){
        if(verificarIntegridad(empleado, 5)){
            String[] id = {empleado.getIdEmpleado()};
            ContentValues cv = new ContentValues();
            cv.put("nombreEmpleado", empleado.getNombreEmpleado());
            cv.put("apellidoEmpleado", empleado.getApellidoEmpleado());
            cv.put("profesion", empleado.getProfesion());
            cv.put("cargo", empleado.getCargo());

            db.update("empleado", cv, "idEmpleado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + empleado.getIdEmpleado() + " no existe";
        }
    }
    public String eliminar(Empleado empleado){
        String regAfectados="filas afectadas= ";
        int contador=0;
        //Verificar si se encuentra en otra tabla
        /*if (verificarIntegridad(areaInteres,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/
        contador+=db.delete("empleado", "idEmpleado='"+empleado.getIdEmpleado()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    //fin crud empleado

    //Inicio Crud asistencia empleado
    public String insertar(AsistenciaEmpleado asistencia){
        String regInsertados="Registro Insertado Nº=  ";
        long contador=0;
        ContentValues emple = new ContentValues();
        emple.put("idAsistenciaEmpleado", asistencia.getIdAsistenciaEmpleado());
        emple.put("asistencia", asistencia.getAsistencia());
        emple.put("empleadoId", asistencia.getEmpleadoId());
        emple.put("capacitacionId", asistencia.getCapacitacionId());

        contador=db.insert("asistenciaEmpleado", null, emple);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Verificar inserción ";

        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public AsistenciaEmpleado consultarAsistenciaEmpleado(String idAsistenciaEmpleado){
        String[] id = {idAsistenciaEmpleado};
        Cursor cursor = db.query("asistenciaEmpleado", camposAsistenciaEmpleado, "idAsistenciaEmpleado = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            AsistenciaEmpleado asistenciaEmpleado = new AsistenciaEmpleado();
            asistenciaEmpleado.setIdAsistenciaEmpleado(cursor.getString(0));
            asistenciaEmpleado.setAsistencia(cursor.getString(1));
            asistenciaEmpleado.setEmpleadoId(cursor.getString(2));
            asistenciaEmpleado.setCapacitacionId(cursor.getString(3));
            return asistenciaEmpleado;
        }else{
            return null;
        }
    }
    public String actualizar(AsistenciaEmpleado asistencia){
        if(verificarIntegridad(asistencia, 7)){
            String[] id = {asistencia.getIdAsistenciaEmpleado()};
            ContentValues cv = new ContentValues();
            cv.put("asistencia", asistencia.getAsistencia());
            cv.put("empleadoId", asistencia.getEmpleadoId());
            cv.put("capacitacionId", asistencia.getCapacitacionId());

            db.update("asistenciaEmpleado", cv, "idAsistenciaEmpleado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + asistencia.getIdAsistenciaEmpleado() + " no existe";
        }
    }
    public String eliminar(AsistenciaEmpleado asistencia){
        String regAfectados="filas afectadas= ";
        int contador=0;
        //Verificar si se encuentra en otra tabla
        /*if (verificarIntegridad(areaInteres,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/
        contador+=db.delete("asistenciaEmpleado", "idAsistenciaEmpleado='"+asistencia.getIdAsistenciaEmpleado()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    //Inicio Crud solicitud
    public String insertar(Solicitud solicitud){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues soli = new ContentValues();
        soli.put("idSolicitud", solicitud.getIdSolicitud());
        soli.put("fechaSolicitud", solicitud.getFechaSolicitud());
        soli.put("estadoSolicitud", solicitud.getEstadoSolicitud());
        soli.put("capacitacionId", solicitud.getCapacitacionId());
        soli.put("empleadoId", solicitud.getEmpleadoId());

        contador=db.insert("solicitud", null, soli);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Verificar inserción= ";

        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public Solicitud consultarSolicitud(String idSolicitud){
        String[] id = {idSolicitud};
        Cursor cursor = db.query("solicitud", camposSolicitud, "idSolicitud = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            Solicitud solicitud = new Solicitud();
            solicitud.setIdSolicitud(cursor.getString(0));
            solicitud.setFechaSolicitud(cursor.getString(1));
            solicitud.setEstadoSolicitud(cursor.getString(2));
            solicitud.setCapacitacionId(cursor.getString(3));
            solicitud.setEmpleadoId(cursor.getString(4));

            return solicitud;
        }else{
            return null;
        }
    }

    public String actualizar(Solicitud solicitud){
        if(verificarIntegridad(solicitud, 6)){
            String[] id = {solicitud.getIdSolicitud()};
            ContentValues cv = new ContentValues();
            cv.put("fechaSolicitud", solicitud.getFechaSolicitud());
            cv.put("estadoSolicitud", solicitud.getEstadoSolicitud());
            cv.put("capacitacionId", solicitud.getCapacitacionId());
            cv.put("empleadoId", solicitud.getEmpleadoId());


            db.update("solicitud", cv, "idSolicitud = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + solicitud.getIdSolicitud() + " no existe";
        }
    }
    public String eliminar(Solicitud solicitud){
        String regAfectados="filas afectadas= ";
        int contador=0;
        //Verificar si se encuentra en otra tabla
        /*if (verificarIntegridad(areaInteres,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/
        contador+=db.delete("solicitud", "idSolicitud='"+solicitud.getIdSolicitud()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }


    //Inicio crud entidad Capacitadora
    public String insertar(EntidadCapacitadora entidadCapacitadora){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues entidad = new ContentValues();
        entidad.put("codigo", entidadCapacitadora.getCodigo());
        entidad.put("nombre", entidadCapacitadora.getNombre());
        entidad.put("descripcion", entidadCapacitadora.getDescripcion());
        entidad.put("telefono", entidadCapacitadora.getTelefono());
        entidad.put("correo", entidadCapacitadora.getCorreo());
        entidad.put("tipo", entidadCapacitadora.getTipo());

        contador=db.insert("entidadCapacitadora", null, entidad);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }
    public String actualizar(EntidadCapacitadora entidadCapacitadora){
        if(verificarIntegridad(entidadCapacitadora, 2)){
            String[] id = {entidadCapacitadora.getCodigo()};
            ContentValues cv = new ContentValues();
            cv.put("nombre", entidadCapacitadora.getNombre());
            cv.put("descripcion", entidadCapacitadora.getDescripcion());
            cv.put("telefono", entidadCapacitadora.getTelefono());
            cv.put("correo", entidadCapacitadora.getCorreo());
            cv.put("tipo",entidadCapacitadora.getTipo());

            db.update("entidadCapacitadora", cv, "codigo = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + entidadCapacitadora.getCodigo() + " no existe";
        }
    }
    public String eliminar(EntidadCapacitadora entidadCapacitadora){
        String regAfectados="filas afectadas= ";
        int contador=0;
        //Verificar si se encuentra en otra tabla
        /*if (verificarIntegridad(areaInteres,3)) {
            contador+=db.delete("ubicacion", "carnet='"+alumno.getCarnet()+"'", null);
        }*/
        if (verificarIntegridad(entidadCapacitadora,23)) {
            contador+=db.delete("capacitador", "idEntidadCapacitadora='"+entidadCapacitadora.getCodigo()+"'", null);
        }
        contador+=db.delete("entidadCapacitadora", "codigo='"+entidadCapacitadora.getCodigo()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public EntidadCapacitadora consultarEntidadCapacitadora(String codigo){
        String[] id = {codigo};
        Cursor cursor = db.query("entidadCapacitadora", camposEntidadCapacitadora, "codigo = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
            entidadCapacitadora.setCodigo(cursor.getString(0));
            entidadCapacitadora.setNombre(cursor.getString(1));
            entidadCapacitadora.setDescripcion(cursor.getString(2));
            entidadCapacitadora.setTelefono(cursor.getString(3));
            entidadCapacitadora.setCorreo(cursor.getString(4));
            entidadCapacitadora.setTipo(cursor.getString(5));

            return entidadCapacitadora;
        }else{
            return null;
        }
    }
    public List<EntidadCapacitadora> getEntidadCapacitadoraList(){
        String sql = " SELECT * FROM entidadCapacitadora" ;

        List<EntidadCapacitadora> listaEntidadCapacitadora = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String codigo = cursor.getString(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String telefono = cursor.getString(3);
                String correo = cursor.getString(4);
                String tipo = cursor.getString(5);
                listaEntidadCapacitadora.add(new EntidadCapacitadora(codigo,nombre,descripcion,telefono,correo,tipo));
            }while (cursor.moveToNext());
        }

        return listaEntidadCapacitadora;
    }
    //FIN entidad capacitadora


    //INICIO CRUD DIPLOMADO
    public String insertar(Diplomado diplomado){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues diplo = new ContentValues();
        diplo.put("idDiplomado", diplomado.getIdDiplomado());
        diplo.put("titulo", diplomado.getTitulo());
        diplo.put("descripcion", diplomado.getDescripcion());
        diplo.put("capacidades", diplomado.getCapacidades());
        contador=db.insert("diplomado", null, diplo);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Diplomado consultarDiplomado(String idDiploma){
        String[] id = {idDiploma};
        Cursor cursor = db.query("diplomado", camposDiplomado, "idDiplomado = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            Diplomado diplomado = new Diplomado();
            diplomado.setIdDiplomado(cursor.getString(0));
            diplomado.setTitulo(cursor.getString(1));
            diplomado.setDescripcion(cursor.getString(2));
            diplomado.setCapacidades(cursor.getString(3));
            return diplomado;
        }else{
            return null;
        }
    }
    public String actualizar(Diplomado diplomado){
        if(verificarIntegridad(diplomado, 17)){
            String[] id = {diplomado.getIdDiplomado()};
            ContentValues cv = new ContentValues();
            cv.put("titulo", diplomado.getTitulo());
            cv.put("descripcion", diplomado.getDescripcion());
            cv.put("capacidades", diplomado.getCapacidades());
            db.update("diplomado", cv, "idDiplomado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + diplomado.getIdDiplomado() + " no existe";
        }
    }

    public String eliminar(Diplomado diplomado){
        String regAfectados="filas afectadas= ";
        int contador=0;
        if(verificarIntegridad(diplomado,19)) {
            contador+=db.delete("areaDiplomado", "idDiplomado='"+diplomado.getIdDiplomado()+"'", null);
        }
        contador+=db.delete("diplomado", "idDiplomado='"+diplomado.getIdDiplomado()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public List<Diplomado> getDiplomadoList(){
        String sql = " SELECT * FROM diplomado" ;

        List<Diplomado> listaDiplomado = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String codigo = cursor.getString(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String capacidades = cursor.getString(3);
                listaDiplomado.add(new Diplomado(codigo,nombre,descripcion,capacidades));
            }while (cursor.moveToNext());
        }

        return listaDiplomado;
    }
    //FIN CRUD DIPLOMADO

    //INICIO CRUD Area DIplomadp
    public String insertar(AreaDiplomado areaDiplomado){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        if(verificarIntegridad(areaDiplomado,20)) {
            ContentValues areadi = new ContentValues();
            areadi.put("idAreaDiplomado", areaDiplomado.getIdAreaDiplomado());
            areadi.put("nombre", areaDiplomado.getNombre());
            areadi.put("descripcion", areaDiplomado.getDescripcion());
            areadi.put("idDiplomado", areaDiplomado.getIdDiplomado());
            contador=db.insert("areaDiplomado", null, areadi);
        }

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public AreaDiplomado consultarAreaDiplomado(String idAreaDiploma){
        String[] id = {idAreaDiploma};
        Cursor cursor = db.query("areaDiplomado", camposAreaDiplomado, "idAreaDiplomado = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            AreaDiplomado areaDiplomado = new AreaDiplomado();
            areaDiplomado.setIdAreaDiplomado(cursor.getString(0));
            areaDiplomado.setNombre(cursor.getString(1));
            areaDiplomado.setDescripcion(cursor.getString(2));
            areaDiplomado.setIdDiplomado(cursor.getString(3));
            return areaDiplomado;
        }else{
            return null;
        }
    }
    public String actualizar(AreaDiplomado areaDiplomado){
        if(verificarIntegridad(areaDiplomado, 18)){
            String[] id = {areaDiplomado.getIdAreaDiplomado()};
            ContentValues cv = new ContentValues();
            cv.put("nombre", areaDiplomado.getNombre());
            cv.put("descripcion", areaDiplomado.getDescripcion());
            cv.put("idDiplomado", areaDiplomado.getIdDiplomado());
            db.update("areaDiplomado", cv, "idAreaDiplomado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + areaDiplomado.getIdAreaDiplomado() + " no existe";
        }
    }

    public String eliminar(AreaDiplomado areaDiplomado){
        String regAfectados="filas afectadas= ";
        int contador=0;
       /* if (verificarIntegridad(alumno,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/ //NO APLICA
        contador+=db.delete("AreaDiplomado", "idAreaDiplomado='"+areaDiplomado.getIdAreaDiplomado()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public List<AreaDiplomado> getAreaDiplomadoList(){
        String sql = " SELECT * FROM areaDiplomado" ;

        List<AreaDiplomado> listaAreaDiplomado = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String codigo = cursor.getString(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String codigoDiplomado = cursor.getString(3);
                listaAreaDiplomado.add(new AreaDiplomado(codigo,nombre,descripcion,codigoDiplomado));
            }while (cursor.moveToNext());
        }

        return listaAreaDiplomado;
    }
    //FIN CRUD AREA DIPLOMADO

    //INICIO CRUD CAPACITADOR
    public String insertar(Capacitador capacitador){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        if(verificarIntegridad(capacitador,21)) {
            ContentValues capa = new ContentValues();
            capa.put("idCapacitador", capacitador.getIdCapacitador());
            capa.put("nombres", capacitador.getNombres());
            capa.put("apellidos", capacitador.getApellidos());
            capa.put("telefono", capacitador.getTelefono());
            capa.put("idEntidadCapacitadora", capacitador.getIdEntidadCapacitadora());
            capa.put("correo", capacitador.getCorreo());
            capa.put("profesion", capacitador.getProfesion());
            contador=db.insert("capacitador", null, capa);
        }

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Capacitador consultarCapacitador(String idCapacitador){
        String[] id = {idCapacitador};
        Cursor cursor = db.query("capacitador", camposCapacitador, "idCapacitador = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            Capacitador capacitador = new Capacitador();
            capacitador.setIdCapacitador(cursor.getString(0));
            capacitador.setNombres(cursor.getString(1));
            capacitador.setApellidos(cursor.getString(2));
            capacitador.setTelefono(cursor.getString(3));
            capacitador.setIdEntidadCapacitadora(cursor.getString(4));
            capacitador.setCorreo(cursor.getString(5));
            capacitador.setProfesion(cursor.getString(6));
            return capacitador;
        }else{
            return null;
        }
    }
    public String actualizar(Capacitador capacitador){
        if(verificarIntegridad(capacitador, 22)){
            String[] id = {capacitador.getIdCapacitador()};
            ContentValues cv = new ContentValues();
            cv.put("nombres", capacitador.getNombres());
            cv.put("apellidos", capacitador.getApellidos());
            cv.put("telefono", capacitador.getTelefono());
            cv.put("idEntidadCapacitadora", capacitador.getIdEntidadCapacitadora());
            cv.put("correo", capacitador.getCorreo());
            cv.put("profesion", capacitador.getProfesion());
            db.update("capacitador", cv, "idCapacitador = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + capacitador.getIdCapacitador() + " no existe";
        }
    }

    public String eliminar(Capacitador capacitador){
        String regAfectados="filas afectadas= ";
        int contador=0;
       /* if (verificarIntegridad(alumno,3)) {
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
        }*/ //NO APLICA HASTA QUE SE HAGA LA RELACION
        contador+=db.delete("capacitador", "idCapacitador='"+capacitador.getIdCapacitador()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public List<Capacitador> getCapacitadorList(){
        String sql = " SELECT * FROM capacitador" ;

        List<Capacitador> listaCapacitador = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String codigo = cursor.getString(0);
                String nombres = cursor.getString(1);
                String apellidos = cursor.getString(2);
                String telefono = cursor.getString(3);
                String identidadcapacitadora = cursor.getString(4);
                String correo = cursor.getString(5);
                String profesion = cursor.getString(6);
                listaCapacitador.add(new Capacitador(codigo,nombres, apellidos,telefono,identidadcapacitadora,correo,profesion));
            }while (cursor.moveToNext());
        }

        return listaCapacitador;
    }

    //FIN CRUD CAPACITADOR

    //INICIO CRUD DIA
    public String insertarDia(Dia dia){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues dias = new ContentValues();
        dias.put("idDia", dia.getIdDia());
        dias.put("nomDia", dia.getNomDia());
        dias.put("fecha", dia.getFecha());
        contador=db.insert("dia", null, dias);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public Dia consultarDia(String idDia){
        String[] id = {idDia};
        Cursor cursor = db.query("dia", camposDia, "idDia = ?",
                id, null, null, null);
        if(cursor.moveToFirst()){
            Dia dia = new Dia();
            dia.setIdDia(cursor.getString(0));
            dia.setNomDia(cursor.getString(1));
            dia.setFecha(cursor.getString(2));
            return dia;
        }else{
            return null;
        }
    }
    public String actualizarDia(Dia dia){
        if(verificarIntegridad(dia, 24)){
            String[] id = {dia.getIdDia()};
            ContentValues cv = new ContentValues();
            cv.put("nomDia", dia.getNomDia());
            cv.put("fecha", dia.getFecha());
            db.update("dia", cv, "idDia = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con codigo " + dia.getIdDia() + " no existe";
        }
    }

    public String eliminarDia(Dia dia){
        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if(verificarIntegridad(dia,25)) {
            contador+=db.delete("horario", "idDia='"+dia.getIdDia()+"'", null);
        }*/ //MODIFICAR ESTO HASTA CREAR TABLA HORARIO
        contador+=db.delete("dia", "idDia='"+dia.getIdDia()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    //FIN CRUD DIA

    //VERIFICACION DE INEGRACION, SIRVE CUANDO ACTUALIZAMOS,
    // CUANDO INGRESEMOS TABLAS CON LLAVES FORANEAS Y CUANDO BORREMOS CAMPOS DEPENDIENTES
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
            //Verificando si existe la entidad capacitadora antes de actualizarla
            case 2:
            {
                EntidadCapacitadora entidadCapacitadora = (EntidadCapacitadora) dato;
                String[] id = {entidadCapacitadora.getCodigo()};
                abrir();
                Cursor c2 = db.query("entidadCapacitadora", null, "codigo = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 3:
            {
                //verificar que al insertar un accesoUsuario exista el codigo del usuario y el codigo de crud
                AccesoUsuario accesoUsuario = (AccesoUsuario) dato;
                String[] id1 = {accesoUsuario.getIdOpcion()};
                String[] id2 = {accesoUsuario.getIdUsuario()};
                //abrir();
                Cursor cursor1 = db.query("opcionCrud", null, "idOpcion = ?", id1, null,
                        null, null);
                Cursor cursor2 = db.query("usuario", null, "idUsuario = ?", id2,
                        null, null, null);
                if(cursor1.moveToFirst() && cursor2.moveToFirst()){
                    //Se encontraron datos
                    return true;
                }
                return false;
            }
            case 5:
            {
                Empleado empleado = (Empleado) dato;
                String[] id = {empleado.getIdEmpleado()};
                abrir();
                Cursor c2 = db.query("empleado", null, "idEmpleado = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 6:
            {
                Solicitud solicitud = (Solicitud) dato;
                String[] id = {solicitud.getIdSolicitud()};
                abrir();
                Cursor c2 = db.query("solicitud", null, "idSolicitud = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }

            case 7:
            {
                AsistenciaEmpleado asistenciaEmpleado = (AsistenciaEmpleado) dato;
                String[] id = {asistenciaEmpleado.getIdAsistenciaEmpleado()};
                abrir();
                Cursor c2 = db.query("asistenciaEmpleado", null, "idAsistenciaEmpleado = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            //Verificando si existe Diplomado antes de actualizarlo
            case 17:
            {
                Diplomado diplomado = (Diplomado) dato;
                String[] id = {diplomado.getIdDiplomado()};
                abrir();
                Cursor c2 = db.query("diplomado", null, "idDiplomado = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            //Verificando si existe Area Diplomado antes de actualizarlo
            case 18:
            {
                AreaDiplomado areaDiplomado = (AreaDiplomado) dato;
                String[] id = {areaDiplomado.getIdAreaDiplomado()};
                String[] id2 = {areaDiplomado.getIdDiplomado()};
                abrir();
                Cursor c2 = db.query("areaDiplomado", null, "idAreaDiplomado = ?", id, null, null,
                        null);
                Cursor c1 = db.query("diplomado", null, "idDiplomado = ?", id2, null, null,
                        null);
                if (c2.moveToFirst() && c1.moveToFirst()) {
                    return true;
                }
                return false;
            }
            // verificar que al borrar un Diplomado no este en algun areaDiplomado
            case 19:
            {
                Diplomado diplomado = (Diplomado) dato;
                Cursor c=db.query(true, "areaDiplomado", new String[] {
                                "idDiplomado" }, "idDiplomado='"+diplomado.getIdDiplomado()+"'",null,
                        null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 20:
            {
                //verificar que al insertar area Diplomado exista dipplomado

                AreaDiplomado areaDiplomado = (AreaDiplomado) dato;
                String[] id1 = {areaDiplomado.getIdDiplomado()};

                Cursor cursor1 = db.query("diplomado", null, "idDiplomado = ?", id1, null,
                        null, null);

                if(cursor1.moveToFirst()){

                    return true;
                }
                return false;
            }

            case 21:
            {
                //verificar que al insertar Capacitador  exista Entidad capacitadora

                Capacitador capacitador = (Capacitador) dato;
                String[] id1 = {capacitador.getIdEntidadCapacitadora()};

                Cursor cursor1 = db.query("entidadCapacitadora", null, "codigo = ?", id1, null,
                        null, null);

                if(cursor1.moveToFirst()){

                    return true;
                }
                return false;
            }

            case 22:
            {
                //verificar que al modificar Capacitador, exista y exista endidad capacitadora
                Capacitador capacitador1 = (Capacitador) dato;
                String[] ids = {capacitador1.getIdCapacitador()};
                String[] id2 = {capacitador1.getIdEntidadCapacitadora()};

                abrir();
                Cursor c1 = db.query("capacitador", null, "idCapacitador = ?", ids, null, null, null);
                Cursor c2 = db.query("entidadCapacitadora", null, "codigo = ?", id2, null, null, null);

                if(c1.moveToFirst() && c2.moveToFirst()){
                    //Se encontraron datos
                    return true;
                }
                return false;
            }
            case 23:
            {   //verificar que al borrar una entidad capacitadora no este en algun capacitador
                EntidadCapacitadora entidadCapacitadora = (EntidadCapacitadora) dato;
                Cursor c=db.query(true, "capacitador", new String[] {
                                "idEntidadCapacitadora" }, "idEntidadCapacitadora='"+entidadCapacitadora.getCodigo()+"'",null,
                        null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }
            //Verificando si existe Dia antes de actualizarlo
            case 24:
            {
                Dia dia = (Dia) dato;
                String[] id = {dia.getIdDia()};
                abrir();
                Cursor c2 = db.query("dia", null, "idDia = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
                    return true;
                }
                return false;
            }// verificar que al borrar un Dia no este en la tabla Horario
            case 25:
            {
                Dia dias = (Dia) dato;
                Cursor c=db.query(true, "horario", new String[] {
                                "idDia" }, "idDia='"+dias.getIdDia()+"'",null,
                        null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }

            default:
                return false;
        }

    }

    //Llenar la base de datos para USUARIOS, OPCIONESCRUD Y ACCESOUSUARIO
    public String llenarBDUsuario()
    {
        //DATOS DE USUARIOS
        final String[] VAidUsuario = {"01","02","03","04","05"};
        final String[] VAnomUsuario = {"GC18090","PT18003","VM13068","AA15020","CZ13016"};
        final String[] VAclave = {"admin","admin","admin","admin","admin"};
        //DATOS DE OPCION USUARIO
        final String[] VBidOpcion = {"010","011","012","013","014",
                "020","021","022","023","024",
                "030","031","032","033","034",
                "040","041","042","043","044",
                "050","051","052","053","054"};
        final String[] VBdesOpcion = {
                "MenuAreaInteres","AdicionAreaInteres","ModificacionAreaInteres","EliminarAreaInteres","ConsultaAreaInteres",
                "MenuEntidadCapacitadora","AdicionEntidadCapacitadora","ModificacionEntidadCapacitadora","EliminarEntidadCapacitadora","ConsultaEntidadCapacitadora",
                "MenuCapacitador","AdicionCapacitador","ModificacionCapacitador","EliminarCapacitador","ConsultaCapacitador",
                "MenuDiplomado","AdicionDiplomado","ModificacionDiplomado","EliminarDiplomado","ConsultaDiplomado",
                "MenuAreaDiplomado","AdicionAreaDiplomado","ModificacionAreaDiplomado","EliminarAreaDiplomado","ConsultaAreaDiplomado",
        };
        final int[] VBnumCrud = {0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4};

        //DATOS DE ACCESO USURAIO
        final String[] VCidOpcion = {"010","011","012","013","014",
                "020","021","022","023","024",
                "030","031","032","033","034",
                "040","041","042","043","044",
                "050","051","052","053","054",
                "010","020","030","040","050"
        };
        final String[] VCidUsuario = {"01","01","01","01","01",
                "01","01","01","01","01",
                "01","01","01","01","01",
                "01","01","01","01","01",
                "01","01","01","01","01",
                "02","02","02","02","02"};



        //DATOS ENTIDAD CAPACITADORA
        final String[] V1codigo={"ENTCA1","ENTCA2","ENTCA3"};
        final String[] V1nombre={"Entidad capacitadora 1","Entidad capacitadora 2","Entidad capacitadora 3"};
        final String[] V1descripcion={"Capacitadora en Ciencias de la computación","Capacitadora en Ciencias médicas","Capacitadora en Ciencias sociales"};
        final String[] V1telefono={"2235-0001","2235-0002","2235-0003"};
        final String[] V1correo={"entCa1@ues.edu.sv","entCa2@ues.edu.sv","entCa3@ues.edu.sv"};
        final String[] V1tipo={"I","E","I"};

        //DATOS DIPLOMADO
        final String[] v2id={"DIP01","DIP02","DIP03"};
        final String[] v2titulo={"Diplomado 1","Diplomado 2","Diplomado 3"};
        final String[] v2descripcion={"Diplomado de Prevencion de ataques DDoS","Diplomado de primeros auxilios","Diplomado de Marketing"};
        final String[] v2capacidades={"Prevencion y reconocimiento de Ataques","primeros auxilios y manejo de la situacion","Estrategias de ventas y publicidad"};

        //DATOS AREA INTERES
        final String[] v3codigo ={"ARINT01","ARINT02","ARINT03"};
        final String[] v3nombre ={"Area interes 1","Area interes 2","Area interes 3"};
        final String[] v3descripcion ={"Descripcion del area de interes 1","Descripcion del area de interes 2","Descripcion del area de interes 3"};

        //DATOS AREA DIPLOMADO
        final String[] v4codigo = {"ADIP1","ADIP2","ADIP3"};
        final String[] v4nombre = {"Area Diplomado 1","Area Diplomado 2","Area Diplomado 3"};
        final String[] v4descripcion = {"Ciencias de la Computacion","Medicina","Humanistica"};
        final String[] v4idDiplomado = {"DIP01","DIP02","DIP03"};

        //DATOS CAPACITADOR
        final String[] v5codigo = {"CAP01","CAP02","CAP03"};
        final String[] v5nombres = {"Miguel Angel","Jose Walter","Alexa Esmeralda"};
        final String[] v5apellidos = {"Galdamez Canales","Perez Tejada","Perez Molina"};
        final String[] v5telefono = {"22350100","22350200","22350300"};
        final String[] v5idEntidad = {"ENTCA1","ENTCA2","ENTCA3"};
        final String[] v5correo = {"gc18090@ues.edu.sv","pt18003@ues.edu.sv","PM18090@ues.edu.sv"};
        final String[] v5profesion = {"Estudiante","Ingeniero","Diseñadora"};

        /* Llenar Facultad */
        final String[] VFid = {"1","2","3"};
        final String[] VFnombre = {"Ingenieria y Arquitectura","Economia","Agronomia"};
        /* Llenar Ubicacion*/
        final String[] VUid = {"1","2"};
        final String[] VUidFacultad = {"3","2"};
        final String[] VUnombre = {"Biblioteca","Edificio K"};
        /* Llenar Tipo Ubicacion*/
        final String[] VTUid ={"1","2","3","4"};
        final String[] VTUnombre ={"Edificio","Aula","Salon","Biblioteca"};
        /* Llenar local */
        final String[] VLid = {"1","2","3"};
        final String[] VLidUbicacion = {"2","1","2"};
        final String[] VLidTipoUbicacion= {"1","2","3"};
        final String[] VLnombre = {"C11 ","A68","Z"};
        abrir();
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM accesoUsuario");

        db.execSQL("DELETE FROM entidadCapacitadora");
        db.execSQL("DELETE FROM areaInteres");
        db.execSQL("DELETE FROM diplomado");
        db.execSQL("DELETE FROM areaDiplomado");
        db.execSQL("DELETE FROM capacitador");

        db.execSQL("DELETE FROM facultad");
        db.execSQL("DELETE FROM ubicacion");
        db.execSQL("DELETE FROM tipoUbicacion");
        db.execSQL("DELETE FROM local");

        Usuario usuario = new Usuario();
        for(int i=0;i<5;i++){
            usuario.setIdUsuario(VAidUsuario[i]);
            usuario.setNomUsuario(VAnomUsuario[i]);
            usuario.setClave(VAclave[i]);
            insertar(usuario);
        }
        OpcionCrud opcionCrud = new OpcionCrud();
        for(int i=0;i<25;i++){
            opcionCrud.setIdOpcion(VBidOpcion[i]);
            opcionCrud.setDesOpcion(VBdesOpcion[i]);
            opcionCrud.setNumCrud(VBnumCrud[i]);
            insertar(opcionCrud);
        }

        AccesoUsuario accesoUsuario = new AccesoUsuario();
        for(int i=0;i<30;i++){
            accesoUsuario.setIdOpcion(VCidOpcion[i]);
            accesoUsuario.setIdUsuario(VCidUsuario[i]);
            insertar(accesoUsuario);
        }
        EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
        for(int i=0;i<3;i++){
            entidadCapacitadora.setCodigo(V1codigo[i]);
            entidadCapacitadora.setNombre(V1nombre[i]);
            entidadCapacitadora.setDescripcion(V1descripcion[i]);
            entidadCapacitadora.setCorreo(V1correo[i]);
            entidadCapacitadora.setTelefono(V1telefono[i]);
            entidadCapacitadora.setTipo(V1tipo[i]);
            insertar(entidadCapacitadora);
        }

        Diplomado diplomado = new Diplomado();
        for(int i=0;i<3;i++){
            diplomado.setIdDiplomado(v2id[i]);
            diplomado.setTitulo(v2titulo[i]);
            diplomado.setDescripcion(v2descripcion[i]);
            diplomado.setCapacidades(v2capacidades[i]);
            insertar(diplomado);
        }

        AreaInteres areaInteres = new AreaInteres();
        for(int i=0;i<3;i++){
            areaInteres.setCodigo(v3codigo[i]);
            areaInteres.setDescripcion(v3descripcion[i]);
            areaInteres.setNombre(v3nombre[i]);
            insertar(areaInteres);
        }

        AreaDiplomado areaDiplomado = new AreaDiplomado();
        for(int i=0;i<3;i++){
            areaDiplomado.setIdAreaDiplomado(v4codigo[i]);
            areaDiplomado.setNombre(v4nombre[i]);
            areaDiplomado.setDescripcion(v4descripcion[i]);
            areaDiplomado.setIdDiplomado(v4idDiplomado[i]);
            insertar(areaDiplomado);
        }

        Capacitador capacitador = new Capacitador();
        for(int i=0;i<3;i++){
            capacitador.setIdCapacitador(v5codigo[i]);
            capacitador.setIdEntidadCapacitadora(v5idEntidad[i]);
            capacitador.setProfesion(v5profesion[i]);
            capacitador.setCorreo(v5correo[i]);
            capacitador.setTelefono(v5telefono[i]);
            capacitador.setApellidos(v5apellidos[i]);
            capacitador.setNombres(v5nombres[i]);
            insertar(capacitador);
        }
        //___________________________________________________________________________________________________________________

        Facultad facultad = new Facultad();
        for(int i=0;i<3;i++)
        {
            facultad.setIdFacultad(VFid[i]);
            facultad.setnombre(VFnombre[i]);
            insertar(facultad);
        }
        Ubicacion ubicacion = new Ubicacion();
        for(int i=0;i<2;i++)
        {
            ubicacion.setIdUbicacion(VUid[i]);
            ubicacion.setIdFacultad(VUidFacultad[i]);
            ubicacion.setnombre(VUnombre[i]);
            insertar(ubicacion);
        }
        TipoUbicacion tipoUbicacion = new TipoUbicacion();
        for (int i=0;i<4;i++)
        {
            tipoUbicacion.setId(VTUid[i]);
            tipoUbicacion.setNombre(VTUnombre[i]);
            insertar(tipoUbicacion);
        }
        Local local = new Local();
        for (int i=0;i<3;i++)
        {
            local.setIdLocal(VLid[i]);
            local.setIdUbicacion(VLidUbicacion[i]);
            local.setIdTipoUbicacion(VLidTipoUbicacion[i]);
            local.setNombre(VLnombre[i]);
            insertar(local);
        }
        //___________________________________________________________________________________________________________________

        cerrar();
        return "Guardo Correctamente";
    }
}

