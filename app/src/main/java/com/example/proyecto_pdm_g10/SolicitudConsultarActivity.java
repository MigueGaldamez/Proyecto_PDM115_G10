package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SolicitudConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdSolicitud;
    EditText editFechaSolicitud;
    EditText editEstadoSolicitud;
    EditText editCapacitacionId;
    EditText editEmpleadoId;


    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_consultar);
        helper = new ControlBDProyecto(this);
        editIdSolicitud = (EditText) findViewById(R.id.editIdSolicitud);
        editFechaSolicitud = (EditText) findViewById(R.id.editFechaSolicitud);
        editEstadoSolicitud = (EditText) findViewById(R.id.editEstadoSolicitud);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);
    }

    public void consultarSolicitud(View v) {
        helper.abrir();
        Solicitud solicitud = helper.consultarSolicitud(editIdSolicitud.getText().toString());
        helper.cerrar();
        if(solicitud == null)
            Toast.makeText(this, "Solicitud con codigo" + editIdSolicitud.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editFechaSolicitud.setText(solicitud.getFechaSolicitud());
            editEstadoSolicitud.setText(solicitud.getEstadoSolicitud());
            editCapacitacionId.setText(solicitud.getCapacitacionId());
            editEmpleadoId.setText(solicitud.getEmpleadoId());


        }
    }
    public void limpiarTexto(View v) {
        editIdSolicitud.setText("");
        editFechaSolicitud.setText("");
        editEstadoSolicitud.setText("");
        editCapacitacionId.setText("");
        editEmpleadoId.setText("");
    }
}