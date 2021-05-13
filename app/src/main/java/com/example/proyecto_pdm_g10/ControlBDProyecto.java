package com.example.proyecto_pdm_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.acl.AclEntry;

public class ControlBDProyecto {
    //PARA CADA TABLA HAY QUE LISTAR LOS CAMPOS
    private static final String[]camposAreaInteres = new String [] {"codigo","nombre","descripcion"};
    private static final String[]camposEntidadCapacitadora = new String []   {"codigo", "nombre", "descripcion", "telefono", "correo"};

    private static final String[]camposOpcionCrud = new String [] {"idOpcion","desOpcion","numCrud"};
    private static final String[]camposUsuario = new String [] {"idUsuario","nomUsuario","clave"};
    private static final String[]camposAccesoUsuario = new String [] {"idOpcion","idUsuario"};
    //____________________________________________________________________________________________________________________________________________________________________________________________________________________
    private static final String[]camposFacultad = new String [] {"id","nombre"};
    private static final String[]camposUbicacion2 = new String[] {"u.id","f.nombre"," u.nombre"};
    private static final String[]camposTipoUbicacion = new String[]{"id","nombre"};
    private static final String[]camposLocal1 = new String[]{"l.id","u.nombre","t.nombre","l.nombre"};
    //________________________________________________________________________________________________________________________________________________________________________________________________________________________


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
                //AQUI AGREGAMOS LAS TABLAS
                db.execSQL("CREATE TABLE areaInteres(codigo VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100));");
                db.execSQL("CREATE TABLE entidadCapacitadora(codigo VARCHAR(6) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100),telefono VARCHAR(20),correo VARCHAR(100));");
                //____________________________________________________________________________________________________________________________________________________________________________________________________________________
                db.execSQL("CREATE TABLE facultad(id VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(35));");
                db.execSQL("CREATE TABLE ubicacion(id VARCHAR(7) NOT NULL ,idFacultad VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idFacultad));");
                db.execSQL("CREATE TABLE tipoUbicacion(id VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(35));");
                db.execSQL("CREATE TABLE local(id VARCHAR(7) NOT NULL ,idUbicacion VARCHAR(7) NOT NULL, idTipoUbicacion VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idUbicacion, idTipoUbicacion));");
                //________________________________________________________________________________________________________________________________________________________________________________________________________________________
                db.execSQL("CREATE TABLE usuario(idUsuario CHAR(2) NOT NULL PRIMARY KEY,nomUsuario VARCHAR(30),clave CHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion CHAR(3) NOT NULL PRIMARY KEY,desOpcion VARCHAR(30),numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario(idUsuario VARCHAR(2) NOT NULL ,idOpcion VARCHAR(3) NOT NULL  ,PRIMARY KEY(idOpcion,idUsuario));");

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
    //____________________________________________________________________________________________________________________________________________________________________________________________________________________
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
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
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
    //Fin crud area de interes

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

            return entidadCapacitadora;
        }else{
            return null;
        }
    }
    //FIN entidad capacitadora

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

            default:
                return false;
        }

    }

    //Llenar la base de datos para USUARIOS, OPCIONESCRUD Y ACCESOUSUARIO
    public String llenarBDUsuario()
    {
        final String[] VAidUsuario = {"01","02","03","04","05"};
        final String[] VAnomUsuario = {"GC18090","PT18003","VM13068","AA15020","CZ13016"};
        final String[] VAclave = {"admin","admin","admin","admin","admin"};

        final String[] VBidOpcion = {"010","011","012","013","014","020","024","034"};
        final String[] VBdesOpcion = {"Menu de Area Interes","Adicion Area Interes","Modificacion Area Interes","Eliminar Area Interes","Consulta Area Interes", "Menu Entidad Capacitadora","Consulta de Entidad Capacitadora","Consulta de Ubicacion"};
        final int[] VBnumCrud = {0,1,2,3,4,0,4,4};

        final String[] VCidOpcion = {"010","011","012","013","014","020","010"};
        final String[] VCidUsuario = {"01","01","01","01","01","01","02"};
        abrir();
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM accesoUsuario");

        Usuario usuario = new Usuario();
        for(int i=0;i<5;i++){
            usuario.setIdUsuario(VAidUsuario[i]);
            usuario.setNomUsuario(VAnomUsuario[i]);
            usuario.setClave(VAclave[i]);
            insertar(usuario);
        }
        OpcionCrud opcionCrud = new OpcionCrud();
        for(int i=0;i<8;i++){
            opcionCrud.setIdOpcion(VBidOpcion[i]);
            opcionCrud.setDesOpcion(VBdesOpcion[i]);
            opcionCrud.setNumCrud(VBnumCrud[i]);
            insertar(opcionCrud);
        }

        AccesoUsuario accesoUsuario = new AccesoUsuario();
        for(int i=0;i<7;i++){
            accesoUsuario.setIdOpcion(VCidOpcion[i]);
            accesoUsuario.setIdUsuario(VCidUsuario[i]);
            insertar(accesoUsuario);
        }
        //___________________________________________________________________________________________________________________
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
        db.execSQL("DELETE FROM facultad");
        db.execSQL("DELETE FROM ubicacion");
        db.execSQL("DELETE FROM tipoUbicacion");
        db.execSQL("DELETE FROM local");


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
