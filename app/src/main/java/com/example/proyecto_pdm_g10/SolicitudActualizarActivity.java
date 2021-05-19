package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitudActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdSolitud;
    EditText editFechaSolicitud;
    EditText editEstadoSolicitud;
    EditText editCapacitacionId;
    EditText editEmpleadoId;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_actualizar);

        helper = new ControlBDProyecto(this);
        editIdSolitud = (EditText) findViewById(R.id.editIdSolicitud);
        editFechaSolicitud = (EditText) findViewById(R.id.editFechaSolicitud);
        editEstadoSolicitud = (EditText) findViewById(R.id.editEstadoSolicitud);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);

    }
    public void actualizarSolicitud(View v) {
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(editIdSolitud.getText().toString());
        solicitud.setFechaSolicitud(editFechaSolicitud.getText().toString());
        solicitud.setEstadoSolicitud(editEstadoSolicitud.getText().toString());
        solicitud.setCapacitacionId(editCapacitacionId.getText().toString());
        solicitud.setEmpleadoId(editEmpleadoId.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(solicitud);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdSolitud.setText("");
        editFechaSolicitud.setText("");
        editEstadoSolicitud.setText("");
        editCapacitacionId.setText("");
        editEmpleadoId.setText("");

    }
}