package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class UbicacionActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editIdFacultad;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_actualizar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdFacultad = (EditText) findViewById(R.id.editidfacultad);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void actualizarUbicacion(View v)
    {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setIdUbicacion(editId.getText().toString());
        ubicacion.setIdFacultad(editIdFacultad.getText().toString());
        ubicacion.setnombre(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(ubicacion);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editId.setText("");
        editIdFacultad.setText("");
        editNombre.setText("");
    }
}