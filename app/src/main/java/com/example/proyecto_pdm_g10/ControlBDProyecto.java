package com.example.proyecto_pdm_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.security.acl.AclEntry;
import java.util.ArrayList;
import java.util.List;

public class ControlBDProyecto {
    //PARA CADA TABLA HAY QUE LISTAR LOS CAMPOS
    private static final String[]camposAreaInteres = new String [] {"codigo","nombre","descripcion"};
    private static final String[]camposEntidadCapacitadora = new String []   {"codigo", "nombre", "descripcion", "telefono", "correo","tipo"};

    private static final String[]camposOpcionCrud = new String [] {"idOpcion","desOpcion","numCrud"};
    private static final String[]camposUsuario = new String [] {"idUsuario","nomUsuario","clave"};
    private static final String[]camposAccesoUsuario = new String [] {"idOpcion","idUsuario"};

    private static final String[]camposDiplomado = new String [] {"idDiplomado","titulo","descripcion","capacidades"};
    private static final String[]camposAreaDiplomado = new String [] {"idAreaDiplomado","nombre","descripcion","idDiplomado"};
    private static final String[]camposCapacitador = new String [] {"idCapacitador","nombres","apellidos","telefono","idEntidadCapacitadora","correo","profesion"};

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
                db.execSQL("CREATE TABLE entidadCapacitadora(codigo VARCHAR(6) NOT NULL PRIMARY KEY,nombre VARCHAR(30),descripcion VARCHAR(100),telefono VARCHAR(20),correo VARCHAR(100),tipo CHAR(1));");

                db.execSQL("CREATE TABLE usuario(idUsuario CHAR(2) NOT NULL PRIMARY KEY,nomUsuario VARCHAR(30),clave CHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion CHAR(3) NOT NULL PRIMARY KEY,desOpcion VARCHAR(30),numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario(idUsuario VARCHAR(2) NOT NULL ,idOpcion VARCHAR(3) NOT NULL  ,PRIMARY KEY(idOpcion,idUsuario));");

                db.execSQL("CREATE TABLE diplomado(idDiplomado CHAR(5) NOT NULL PRIMARY KEY,titulo VARCHAR(30),descripcion VARCHAR(100),capacidades VARCHAR(100));");
                db.execSQL("CREATE TABLE areaDiplomado(idAreaDiplomado CHAR(5) NOT NULL PRIMARY KEY,nombre VARCHAR(20),descripcion VARCHAR(100),idDiplomado VARCHAR(5));");
                db.execSQL("CREATE TABLE capacitador(idCapacitador CHAR(5) NOT NULL PRIMARY KEY,nombres VARCHAR(40),apellidos VARCHAR(40),telefono VARCHAR(20),idEntidadCapacitadora VARCHAR(6),correo VARCHAR(100),profesion VARCHAR(30));");

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

            default:
                return false;
        }

    }

    //Llenar la base de datos para USUARIOS, OPCIONESCRUD Y ACCESOUSUARIO
    public String llenarBDUsuario() {
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


        abrir();
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM accesoUsuario");

        db.execSQL("DELETE FROM entidadCapacitadora");
        db.execSQL("DELETE FROM areaInteres");
        db.execSQL("DELETE FROM diplomado");
        db.execSQL("DELETE FROM areaDiplomado");
        db.execSQL("DELETE FROM capacitador");

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

        cerrar();
        return "Guardo Correctamente";
    }

}
