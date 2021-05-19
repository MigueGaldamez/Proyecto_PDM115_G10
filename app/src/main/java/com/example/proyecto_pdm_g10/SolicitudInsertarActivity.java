package com.example.proyecto_pdm_g10;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitudInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdSolicitud;
    EditText editFechaSolicitud;
    EditText editEstadoSolicitud;
    EditText editCapacitacionId;
    EditText editEmpleadoId;


    //   ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    //  String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_insertar);
        helper = new ControlBDProyecto(this);
        editIdSolicitud = (EditText) findViewById(R.id.editIdSolicitud);
        editFechaSolicitud = (EditText) findViewById(R.id.editFechaSolicitud);
        editEstadoSolicitud = (EditText) findViewById(R.id.editEstadoSolicitud);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);


    }
    public void insertarSolicitud(View v) {
        String idSolicitud = editIdSolicitud.getText().toString();
        String fechaSolicitud = editFechaSolicitud.getText().toString();
        String estadoSolicitud = editEstadoSolicitud.getText().toString();
        String capacitacionId = editCapacitacionId.getText().toString();
        String empleadoId = editEmpleadoId.getText().toString();

        String regInsertados;
        Solicitud solicitud=new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setFechaSolicitud(fechaSolicitud);
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setCapacitacionId(capacitacionId);
        solicitud.setEmpleadoId(empleadoId);

        helper.abrir();
        regInsertados=helper.insertar(solicitud);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editIdSolicitud.setText("");
        editFechaSolicitud.setText("");
        editEstadoSolicitud.setText("");
        editCapacitacionId.setText("");
        editEmpleadoId.setText("");

    }


}
