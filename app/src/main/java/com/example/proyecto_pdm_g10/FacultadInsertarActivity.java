package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FacultadInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultad_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void insertarFacultad(View v)
    {
        String id = editId.getText().toString();
        String nombre=editNombre.getText().toString();
        String regInsertados;
        Facultad facultad=new Facultad();
        facultad.setIdFacultad(id);
        facultad.setnombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(facultad);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }

}