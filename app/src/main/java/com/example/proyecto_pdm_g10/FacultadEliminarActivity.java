package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;

public class FacultadEliminarActivity extends Activity
{
    EditText editId;
    ControlBDProyecto controlhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultad_eliminar);
        controlhelper=new ControlBDProyecto (this);
        editId=(EditText)findViewById(R.id.editid);
    }
    public void eliminarFacultad(View v)
    {
        String regEliminadas;
        Facultad facultad=new Facultad();
        facultad.setIdFacultad(editId.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(facultad);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }


}