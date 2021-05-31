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

public class CapacitadorEliminarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;
    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/capacitador/ws_capacitador_delete.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/capacitador/ws_capacitador_delete.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitador_eliminar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
    }
    public void eliminarCapacitador(View v){
        String regEliminadas;
        Capacitador capacitador=new Capacitador();
        capacitador.setIdCapacitador(editIdCapacitador.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(capacitador);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void eliminarCapacitadorExterno(View v)
    {
        String codigo = editIdCapacitador.getText().toString();
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_capacitador=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_capacitador=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }

    }
}