package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SolicitudEliminarActivity extends AppCompatActivity {
    ControlBDProyecto helper;
    EditText editIdSolicitud;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_eliminar);
        helper = new ControlBDProyecto(this);
        editIdSolicitud = (EditText) findViewById(R.id.editIdSolicitud);

    }
    public void eliminarSolicitud(View v){
        String regEliminadas;
        Solicitud solicitud=new Solicitud();
        solicitud.setIdSolicitud(editIdSolicitud.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(solicitud);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}