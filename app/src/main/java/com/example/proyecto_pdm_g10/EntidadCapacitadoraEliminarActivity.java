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

public class EntidadCapacitadoraEliminarActivity extends Activity {
    EditText editCodigo;
    ControlBDProyecto helper;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/entidadCapacitadora/ws_entidadCapacitadora_delete.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/entidadCapacitadora/ws_entidadCapacitadora_delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_eliminar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);

    }
    public void eliminarEntidadCapacitadora(View v){
        String regEliminadas;
        EntidadCapacitadora entidadCapacitadora=new EntidadCapacitadora();
        entidadCapacitadora.setCodigo(editCodigo.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(entidadCapacitadora);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void eliminarEntidadCapacitadoraExterno(View v)
    {
        String codigo = editCodigo.getText().toString();
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_entidad=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_entidad=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }

    }
}