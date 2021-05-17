package com.example.proyecto_pdm_g10;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class FacultadActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultad_actualizar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void actualizarFacultad(View v)
    {
        Facultad facultad = new Facultad();
        facultad.setIdFacultad(editId.getText().toString());
        facultad.setnombre(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(facultad);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }
}