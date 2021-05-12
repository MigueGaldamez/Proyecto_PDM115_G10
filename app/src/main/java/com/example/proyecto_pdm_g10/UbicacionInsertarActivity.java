package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class UbicacionInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editIdFacultad;
    EditText editNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdFacultad = (EditText) findViewById(R.id.editidfacultad);
        editNombre = (EditText) findViewById(R.id.editNombre);

    }
    public void insertarUbicacion(View v) {
        String regInsertados;
        String id=editId.getText().toString();
        String idFacultad=editIdFacultad.getText().toString();
        String nombre=editNombre.getText().toString();
        Ubicacion ubicacion= new Ubicacion();
        ubicacion.setIdUbicacion(id);
        ubicacion.setIdFacultad(idFacultad);
        ubicacion.setnombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(ubicacion);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editId.setText("");
        editIdFacultad.setText("");
        editNombre.setText("");
    }

}