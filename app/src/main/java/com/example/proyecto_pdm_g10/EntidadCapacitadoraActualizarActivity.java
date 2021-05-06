package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EntidadCapacitadoraActualizarActivity extends AppCompatActivity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    EditText editTelefono;
    EditText editCorreo;
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
    }
    public void actualizarEntidadCapacitadora(View v) {
        EntidadCapacitadora entidadCapacitadora = new EntidadCapacitadora();
        entidadCapacitadora.setCodigo(editCodigo.getText().toString());
        entidadCapacitadora.setNombre(editNombre.getText().toString());
        entidadCapacitadora.setDescripcion(editDescripcion.getText().toString());
        entidadCapacitadora.setTelefono(editTelefono.getText().toString());
        entidadCapacitadora.setCorreo(editCorreo.getText().toString());

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