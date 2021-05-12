package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_actualizar);

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
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editTelefono.setText("");
        editCorreo.setText("");

    }
}