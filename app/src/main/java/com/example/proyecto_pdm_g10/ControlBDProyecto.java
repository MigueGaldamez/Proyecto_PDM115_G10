package com.example.proyecto_pdm_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String[]camposUbicacion = new String [] {"id","idFacultad","nombre"};
    private  static final String[]camposUbicacion2 = new String[] {"u.id","f.nombre"," u.nombre"};

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
            return "Registro con carnet " + facultad.getIdFacultad() + " no existe";
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
        if(verificarIntegridad2(ubicacion,5) && verificarIntegridad2(ubicacion,1))
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
        if(verificarIntegridad2(ubicacion, 2))
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

    private boolean verificarIntegridad2(Object dato, int relacion) throws SQLException
    {
        switch(relacion)
        {
            case 1:
            {
                //verificar que al insertar ubicacion exista carnet del facultad
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
            case 2:
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
            /*case 5:
            {
                Ubicacion ubicacion = (Ubicacion) dato;
                Cursor c=db.query(true, "ubicacion", new String[] {"idFacultad" }, "idFacultad='"+facultad.getIdFacultad()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else
                    return false;
            }*/
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
            case 5:
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
        final String[] VBdesOpcion = {"Menu de Area Interes","Adicion Area Interes","Modificacion Area Interes","Eliminar Area Interes","Consulta Area Interes",
                "Menu Entidad Capacitadora","Consulta de Entidad Capacitadora","Consulta de Ubicacion"};
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
        final String[] VFid = {"1","2","3"};
        final String[] VFnombre = {"Ingenieria y Arquitectura","Economia","Agronomia"};

        final String[] VUid = {"1","2"};
        final String[] VUidFacultad = {"3","2"};
        final String[] VUnombre = {"Biblioteca","Edificio K"};
        abrir();
        db.execSQL("DELETE FROM facultad");
        db.execSQL("DELETE FROM ubicacion");

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

        //___________________________________________________________________________________________________________________

        cerrar();
        return "Guardo Correctamente";
    }

}
