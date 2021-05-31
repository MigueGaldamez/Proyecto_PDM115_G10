package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EntidadCapacitadoraActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    EditText editTelefono;
    EditText editCorreo;

    RadioButton radioExterna,radioInterna;
    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/entidadCapacitadora/ws_entidadCapacitadora_update.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/entidadCapacitadora/ws_entidadCapacitadora_update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_actualizar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);

        radioExterna = (RadioButton) findViewById(R.id.radio_externa);
        radioInterna = (RadioButton) findViewById(R.id.radio_interna);

    }
    public void actualizarEntidadCapacitadora(View v) {
        EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
        entidadCapacitadora.setCodigo(editCodigo.getText().toString());
        entidadCapacitadora.setNombre(editNombre.getText().toString());
        entidadCapacitadora.setDescripcion(editDescripcion.getText().toString());
        entidadCapacitadora.setTelefono(editTelefono.getText().toString());
        entidadCapacitadora.setCorreo(editCorreo.getText().toString());
        if(radioExterna.isChecked())
        {
            entidadCapacitadora.setTipo("E");
        }
        if(radioInterna.isChecked())
        {
            entidadCapacitadora.setTipo("I");
        }
        helper.abrir();
        String estado = helper.actualizar(entidadCapacitadora);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void actualizarEntidadCapacitadoraExterno(View v)
    {
        String codigo = editCodigo.getText().toString();
        String nombre = editNombre.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String telefono = editTelefono.getText().toString();
        String correo = editCorreo.getText().toString();
        String tipo = "";
        if(radioExterna.isChecked())
        {
            tipo="E";
        }
        if(radioInterna.isChecked())
        {
            tipo ="I";
        }
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_entidad=" + codigo + "&nombre_entidad="
                        + nombre + "&descripcion=" + descripcion +"&telefono="+telefono+"&correo="+correo+"&tipo="+tipo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_entidad=" + codigo + "&nombre_entidad="
                        + nombre + "&descripcion=" + descripcion +"&telefono="+telefono+"&correo="+correo+"&tipo="+tipo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }
    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editTelefono.setText("");
        editCorreo.setText("");

    }
}