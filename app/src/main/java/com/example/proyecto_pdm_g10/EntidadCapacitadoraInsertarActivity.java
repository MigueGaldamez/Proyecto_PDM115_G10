package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EntidadCapacitadoraInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    EditText editTelefono;
    EditText editCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_insertar);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
    }
    public void insertarEntidadCapacitadora(View v) {
        String codigo = editCodigo.getText().toString();
        String nombre = editNombre.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String telefono = editTelefono.getText().toString();
        String correo = editCorreo.getText().toString();
        String regInsertados;
        EntidadCapacitadora entidadCapacitadora=new EntidadCapacitadora();
        entidadCapacitadora.setCodigo(codigo);
        entidadCapacitadora.setNombre(nombre);
        entidadCapacitadora.setDescripcion(descripcion);
        entidadCapacitadora.setTelefono(telefono);
        entidadCapacitadora.setCorreo(correo);
        helper.abrir();
        regInsertados=helper.insertar(entidadCapacitadora);
        helper.cerrar();
        Toast.makeText(this, regInsertados , Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editTelefono.setText("");
        editCorreo.setText("");

    }
}