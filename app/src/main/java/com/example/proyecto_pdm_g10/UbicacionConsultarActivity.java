package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UbicacionConsultarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editIdFacultad;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_consultar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdFacultad = (EditText) findViewById(R.id.editidfacultad);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void consultarUbicacion(View v) {
        helper.abrir();
        Ubicacion ubicacion = helper.consultarUbicacion(editId.getText().toString());
        helper.cerrar();
        if(ubicacion == null)
            Toast.makeText(this, "Ubicacion con id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(ubicacion.getnombre());
            editIdFacultad.setText(ubicacion.getIdFacultad());
        }
    }
    public void limpiarTexto(View v) {
        editId.setText("");
        editIdFacultad.setText("");
        editNombre.setText("");
    }

}