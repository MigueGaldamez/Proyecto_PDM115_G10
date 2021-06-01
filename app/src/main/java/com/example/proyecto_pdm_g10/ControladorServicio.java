package com.example.proyecto_pdm_g10;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ControladorServicio {

    public static String obtenerRespuestaPeticion(String url, Context ctx) {
        String respuesta = " ";
        // Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 3000);
        HttpConnectionParams.setSoTimeout(parametros, 5000);
        // Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpRespuesta = cliente.execute(httpGet);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                HttpEntity entidad = httpRespuesta.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }
    public static String obtenerRespuestaPost(String url, JSONObject obj,
                                              Context ctx) {
        String respuesta = " ";
        try {
            HttpParams parametros = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(parametros, 3000);
            HttpConnectionParams.setSoTimeout(parametros, 5000);
            HttpClient cliente = new DefaultHttpClient(parametros);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("content-type", "application/json");
            StringEntity nuevaEntidad = new StringEntity(obj.toString());
            httpPost.setEntity(nuevaEntidad);
            Log.v("Peticion",url);
            Log.v("POST", httpPost.toString());
            HttpResponse httpRespuesta = cliente.execute(httpPost);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                respuesta = Integer.toString(codigoEstado);
                Log.v("respuesta",respuesta);
            }
            else{
                Log.v("respuesta",Integer.toString(codigoEstado));
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }
    //INICIO
    public static void ejecutarConsulta(String peticion, Context ctx) {
        String peticion2 = peticion.replaceAll(" ","%20");
        String json = obtenerRespuestaPeticion(peticion2, ctx);
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Consulta Ejecutada con exito", Toast.LENGTH_LONG)
                        .show();
            else
                Toast.makeText(ctx, "Error registro duplicado",
                        Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static AreaInteres consultarAreaInteresExterna(String peticion, Context ctx) {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        AreaInteres areaInteres = new AreaInteres();
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            //int respuesta = resultado.getInt("resultado");
            areaInteres.setCodigo(resultado.getString("ID_AREA_INTERES"));
            areaInteres.setNombre(resultado.getString("NOMBREAREA"));
            areaInteres.setDescripcion(resultado.getString("DESCRIPCIONAREA"));
            if (!areaInteres.getNombre().equals(""))
                Toast.makeText(ctx, "Consulta Ejecutada con exito", Toast.LENGTH_LONG)
                        .show();
            else
                Toast.makeText(ctx, "Error registro duplicado",
                        Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return areaInteres;
    }
    public static List<AreaInteres> obtenerListAreaInteresExterno(String peticion, Context ctx)
    {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        List<AreaInteres> listaAreaInteres = new ArrayList<AreaInteres>();
        try {
            JSONArray areasJSON = new JSONArray(json);
            for (int i = 0; i < areasJSON.length(); i++) {

                JSONObject obj = areasJSON.getJSONObject(i);
                AreaInteres areaInteres = new AreaInteres();
                areaInteres.setCodigo(obj.getString("ID_AREA_INTERES"));
                areaInteres.setNombre(obj.getString("NOMBREAREA"));
                areaInteres.setDescripcion(obj.getString("DESCRIPCIONAREA"));
                listaAreaInteres.add(areaInteres);
            }
            return listaAreaInteres;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }
    //ENTIDAD CAPACITADORA
    public static EntidadCapacitadora consultarEntidadCapacitadoraExterna(String peticion, Context ctx) {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            //int respuesta = resultado.getInt("resultado");
            entidadCapacitadora.setCodigo(resultado.getString("ID_ENTIDAD"));
            entidadCapacitadora.setNombre(resultado.getString("NOMBRE_ENTIDAD"));
            entidadCapacitadora.setDescripcion(resultado.getString("DESCRIPCIONENTIDAD"));
            entidadCapacitadora.setTelefono(resultado.getString("TELEFONOENTIDAD"));
            entidadCapacitadora.setCorreo(resultado.getString("CORREOENTIDAD"));
            entidadCapacitadora.setTipo(resultado.getString("TIPOENTIDAD"));

            if (!entidadCapacitadora.getNombre().equals(""))
                Toast.makeText(ctx, "Consulta Ejecutada con exito", Toast.LENGTH_LONG)
                        .show();
            else
                Toast.makeText(ctx, "Error registro duplicado",
                        Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entidadCapacitadora;
    }
    public static List<EntidadCapacitadora> obtenerListEntidadCapacitadoraExterno(String peticion, Context ctx)
    {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        List<EntidadCapacitadora> listaEntidadCapacitadora = new ArrayList<EntidadCapacitadora>();
        try {
            JSONArray entidadesJSON = new JSONArray(json);
            for (int i = 0; i < entidadesJSON.length(); i++) {

                JSONObject obj = entidadesJSON.getJSONObject(i);
                EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
                entidadCapacitadora.setCodigo(obj.getString("ID_ENTIDAD"));
                entidadCapacitadora.setNombre(obj.getString("NOMBRE_ENTIDAD"));
                entidadCapacitadora.setDescripcion(obj.getString("DESCRIPCIONENTIDAD"));
                entidadCapacitadora.setTelefono(obj.getString("TELEFONOENTIDAD"));
                entidadCapacitadora.setCorreo(obj.getString("CORREOENTIDAD"));
                entidadCapacitadora.setTipo(obj.getString("TIPOENTIDAD"));
                listaEntidadCapacitadora.add(entidadCapacitadora);
            }
            return listaEntidadCapacitadora;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }
    //DIPLOMADO
    public static  Diplomado consultarDiplomadoExterno(String peticion, Context ctx) {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        Diplomado diplomado = new Diplomado();
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            //int respuesta = resultado.getInt("resultado");
            diplomado.setIdDiplomado(resultado.getString("ID_DIPLOMADO"));
            diplomado.setTitulo(resultado.getString("TITULO"));
            diplomado.setDescripcion(resultado.getString("DESCRIPCIONDIPLOMADO"));
            diplomado.setCapacidades(resultado.getString("CAPACIDADES"));

            if (!diplomado.getTitulo().equals(""))
                Toast.makeText(ctx, "Consulta Ejecutada con exito", Toast.LENGTH_LONG)
                        .show();
            else
                Toast.makeText(ctx, "Error registro duplicado",
                        Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return diplomado;
    }
    public static List<Diplomado> obtenerListaDiplomadoExterno(String peticion, Context ctx)
    {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        List<Diplomado> listaDiplomado = new ArrayList<Diplomado>();
        try {
            JSONArray diplomadosJSON = new JSONArray(json);
            for (int i = 0; i < diplomadosJSON.length(); i++) {

                JSONObject obj = diplomadosJSON.getJSONObject(i);
                Diplomado diplomado = new Diplomado();
                diplomado.setIdDiplomado(obj.getString("ID_DIPLOMADO"));
                diplomado.setTitulo(obj.getString("TITULO"));
                diplomado.setDescripcion(obj.getString("DESCRIPCIONDIPLOMADO"));
                diplomado.setCapacidades(obj.getString("CAPACIDADES"));

                listaDiplomado.add(diplomado);
            }
            return listaDiplomado;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }

    //CONSULTARCAPACITADOR
    public static  Capacitador consultarCapacitadorExterno(String peticion, Context ctx) {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        Capacitador capacitador = new Capacitador();
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            //int respuesta = resultado.getInt("resultado");
            capacitador.setNombres(resultado.getString("NOMBRES_CAPACITADOR"));
            capacitador.setApellidos(resultado.getString("APELLIDOS_CAPACITADOR"));
            capacitador.setIdEntidadCapacitadora(resultado.getString("ID_ENTIDAD"));
            capacitador.setCapacitacionesDadas(resultado.getInt("CAPACIIMPARTIDAS"));
            capacitador.setTelefono(resultado.getString("TELEFONOCAPACITADOR"));
            capacitador.setCorreo(resultado.getString("CORREOCAPACITADOR"));
            capacitador.setProfesion(resultado.getString("PROFESION"));
            capacitador.setIdCapacitador(resultado.getString("ID_CAPACITADOR"));
            if (!capacitador.getNombres().equals(""))
                Toast.makeText(ctx, "Consulta Ejecutada con exito", Toast.LENGTH_LONG)
                        .show();
            else
                Toast.makeText(ctx, "Error registro duplicado",
                        Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return capacitador;
    }
    public static List<Capacitador> obtenerListaCapacitadorExterno(String peticion, Context ctx)
    {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        List<Capacitador> listaCapacitador = new ArrayList<Capacitador>();
        try {
            JSONArray capacitadorsJSON = new JSONArray(json);
            for (int i = 0; i < capacitadorsJSON.length(); i++) {

                JSONObject obj = capacitadorsJSON.getJSONObject(i);
                Capacitador capacitador = new Capacitador();
                capacitador.setNombres(obj.getString("NOMBRES_CAPACITADOR"));
                capacitador.setApellidos(obj.getString("APELLIDOS_CAPACITADOR"));
                capacitador.setIdEntidadCapacitadora(obj.getString("ID_ENTIDAD"));
                capacitador.setCapacitacionesDadas(obj.getInt("CAPACIIMPARTIDAS"));
                capacitador.setTelefono(obj.getString("TELEFONOCAPACITADOR"));
                capacitador.setCorreo(obj.getString("CORREOCAPACITADOR"));
                capacitador.setProfesion(obj.getString("PROFESION"));
                capacitador.setIdCapacitador(obj.getString("ID_CAPACITADOR"));

                listaCapacitador.add(capacitador);
            }
            return listaCapacitador;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }
    //INUTIL POR AHORA
    public static String obtenerPromedioJSON(String json, Context ctx) {
        try {
            JSONArray objs = new JSONArray(json);
            if (objs.length() != 0)
                //NOTAFINAL PROMEDIO
                return objs.getJSONObject(0).getString("PROMEDIO");
            else {
                Toast.makeText(ctx, "Error carnet no existe", Toast.LENGTH_LONG)
                        .show();
                return " ";
            }
        } catch (JSONException e) {
            Toast.makeText(ctx, "Error con la respuesta JSON",
                    Toast.LENGTH_LONG).show();
            return " ";
        }
    }

    //para los combobox
    public  static ArrayList<String> consultarListaObjeto_Externo(int tipo,String peticion, Context ctx) {
        ArrayList<String> listaObjeto = new ArrayList<String>();
        String json = obtenerRespuestaPeticion(peticion, ctx);
        switch (tipo) {
            //CASO DE CAPACITADOR
            case 1: {
                ArrayList<EntidadCapacitadora> objetoList = new ArrayList<EntidadCapacitadora>();
                try {
                    JSONArray entidadesJSON = new JSONArray(json);
                    for (int i = 0; i < entidadesJSON.length(); i++) {

                        JSONObject obj = entidadesJSON.getJSONObject(i);
                        EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
                        entidadCapacitadora.setCodigo(obj.getString("ID_ENTIDAD"));
                        entidadCapacitadora.setNombre(obj.getString("NOMBRE_ENTIDAD"));
                        entidadCapacitadora.setDescripcion(obj.getString("DESCRIPCIONENTIDAD"));
                        entidadCapacitadora.setTelefono(obj.getString("TELEFONOENTIDAD"));
                        entidadCapacitadora.setCorreo(obj.getString("CORREOENTIDAD"));
                        entidadCapacitadora.setTipo(obj.getString("TIPOENTIDAD"));
                        objetoList.add(entidadCapacitadora);
                    }
                    listaObjeto.add("Seleccione");
                    for (int i = 0; i < objetoList.size(); i++) {
                        listaObjeto.add(objetoList.get(i).getCodigo() + " - " + objetoList.get(i).getNombre());
                    }

                } catch (Exception e) {
                    Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                            .show();
                    return null;
                }
            }
        }

        return  listaObjeto;
    }

}
