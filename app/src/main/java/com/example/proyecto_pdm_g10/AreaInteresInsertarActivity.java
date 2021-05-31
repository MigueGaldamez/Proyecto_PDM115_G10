package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class AreaInteresInsertarActivity extends Activity {

    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/areaInteres/ws_areaInteres_insert.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/areaInteres/ws_areaInteres_insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_interes_insertar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
    }
    public void insertarAreaInteres(View v) {
        String codigo = editCodigo.getText().toString();
        String nombre = editNombre.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String regInsertados;
        AreaInteres areaInteres=new AreaInteres();
        areaInteres.setCodigo(codigo);
        areaInteres.setNombre(nombre);
        areaInteres.setDescripcion(descripcion);
        helper.abrir();
        regInsertados=helper.insertar(areaInteres);
        helper.cerrar();
        Toast.makeText(this, regInsertados + nombre, Toast.LENGTH_SHORT).show();

    }
    public void insertarAreaInteresExterno(View v)
    {
        String codigo = editCodigo.getText().toString();
        String nombre = editNombre.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String url = null;
        JSONObject datosAreaInteres = new JSONObject();
        JSONObject areaInteres = new JSONObject();
        //?id_area=ARINT2&nombre_area=AreaInteres1&descripcion=Descripcion01
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_area=" + codigo + "&nombre_area="
                        + nombre + "&descripcion=" + descripcion;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_area=" + codigo + "&nombre_area="
                        + nombre + "&descripcion=" + descripcion;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }
    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");

    }
}