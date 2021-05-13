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

    private static final String[]camposDiplomado = new String [] {"idDiplomado","titulo","descripcion","capacidades"};
    private static final String[]camposAreaDiplomado = new String [] {"idAreaDiplomado","nombre","descripcion","idDiplomado"};
    private static final String[]camposCapacitador = new String [] {"idCapacitador","nombres","apellidos","telefono","idEntidadCapacitadora","correo","profesion"};
    private static final String[]camposEmpleado=new String[] {"idEmpleado", "nombreEmpleado","apellidoEmpleado","profesion","cargo"};

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

                db.execSQL("CREATE TABLE usuario(idUsuario CHAR(2) NOT NULL PRIMARY KEY,nomUsuario VARCHAR(30),clave CHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion CHAR(3) NOT NULL PRIMARY KEY,desOpcion VARCHAR(30),numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario(idUsuario VARCHAR(2) NOT NULL ,idOpcion VARCHAR(3) NOT NULL  ,PRIMARY KEY(idOpcion,idUsuario));");

                db.execSQL("CREATE TABLE diplomado(idDiplomado CHAR(5) NOT NULL PRIMARY KEY,titulo VARCHAR(30),descripcion VARCHAR(100),capacidades VARCHAR(100));");
                db.execSQL("CREATE TABLE areaDiplomado(idAreaDiplomado CHAR(5) NOT NULL PRIMARY KEY,nombre VARCHAR(20),descripcion VARCHAR(100),idDiplomado VARCHAR(5));");
                db.execSQL("CREATE TABLE capacitador(idCapacitador CHAR(5) NOT NULL PRIMARY KEY,nombres VARCHAR(40),apellidos VARCHAR(40),telefono VARCHAR(20),idEntidadCapacitadora VARCHAR(6),correo VARCHAR(100),profesion VARCHAR(30));");

                db.execSQL("CREATE TABLE empleado(idEmpleado CHAR(5) NOT NULL PRIMARY KEY, nombreEmpleado VARCHAR(40),apellidoEmpleado VARCHAR(40),profesion VARCHAR(20),cargo VARCHAR(20));");

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
            regInsertados= "Error al Insertar el registro pendejo, Verificar inserción= "+ contador+" nombre= "+ empleado.getNombreEmpleado()+" apellido= "+ empleado.getApellidoEmpleado()+" Profesion= "+ empleado.getProfesion();

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
        if(verificarIntegridad(empleado, 1)){
            String[] id = {empleado.getIdEmpleado()};
            ContentValues cv = new ContentValues();
            cv.put("nombre empleado", empleado.getNombreEmpleado());
            cv.put("apellido", empleado.getApellidoEmpleado());
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
            contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
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
    //FIN CRUD AREA DIPLOMADO
    

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

            //Verificando si Diplomado antes de actualizarlo
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
            //Verificando si Area Diplomado antes de actualizarlo
            case 18:
            {
                AreaDiplomado areaDiplomado = (AreaDiplomado) dato;
                String[] id = {areaDiplomado.getIdAreaDiplomado()};
                abrir();
                Cursor c2 = db.query("areaDiplomado", null, "idAreaDiplomado = ?", id, null, null,
                        null);
                if (c2.moveToFirst()) {
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


            default:
                return false;
        }

    }

    //Llenar la base de datos para USUARIOS, OPCIONESCRUD Y ACCESOUSUARIO
    public String llenarBDUsuario() {
        final String[] VAidUsuario = {"01","02","03","04","05"};
        final String[] VAnomUsuario = {"GC18090","PT18003","VM13068","AA15020","CZ13016"};
        final String[] VAclave = {"admin","admin","admin","admin","admin"};

        final String[] VBidOpcion = {"010","011","012","013","014","020","024","034"};
        final String[] VBdesOpcion = {"Menu de Area Interes","Adicion Area Interes","Modificacion Area Interes","Eliminar Area Interes","Consulta Area Interes",
                "Menu Entidad Capacitadora","Consulta de Entidad Capacitadora","Consulta de Nota"};
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


        cerrar();
        return "Guardo Correctamente";
    }

}
