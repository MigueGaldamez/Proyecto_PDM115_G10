package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EntidadCapacitadoraConsultarActivity extends AppCompatActivity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    EditText editTelefono;
    EditText editCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_consultar);

        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
    }
    public void consultarEntidadCapacitadora(View v) {
        helper.abrir();
        EntidadCapacitadora entidadCapacitadora = helper.consultarEntidadCapacitadora(editCodigo.getText().toString());
        helper.cerrar();
        if(entidadCapacitadora == null)
            Toast.makeText(this, "Entidad capacitadora con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(entidadCapacitadora.getNombre());
            editDescripcion.setText(entidadCapacitadora.getDescripcion());
            editTelefono.setText(entidadCapacitadora.getTelefono());
            editCorreo.setText(entidadCapacitadora.getCorreo());

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