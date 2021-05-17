package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AsistenciaEmpleadoConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAsistenciaEmpleado;
    EditText editAsistencia;
    EditText editEmpleadoId;
    EditText editCapacitacionId;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_empleado_consultar);
        helper = new ControlBDProyecto(this);
        editIdAsistenciaEmpleado = (EditText) findViewById(R.id.editIdAsistenciaEmpleado);
        editAsistencia = (EditText) findViewById(R.id.editAsistencia);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);
    }
    public void consultarAsistenciaEmpleado(View v) {
        helper.abrir();
        AsistenciaEmpleado asistenciaEmpleado = helper.consultarAsistenciaEmpleado(editIdAsistenciaEmpleado.getText().toString());
        helper.cerrar();
        if(asistenciaEmpleado == null)
            Toast.makeText(this, "Asistencia Empleado con codigo" + editIdAsistenciaEmpleado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editAsistencia.setText(asistenciaEmpleado.getAsistencia());
            editEmpleadoId.setText(asistenciaEmpleado.getEmpleadoId());
            editCapacitacionId.setText(asistenciaEmpleado.getCapacitacionId());
        }
    }
    public void limpiarTexto(View v) {
        editIdAsistenciaEmpleado.setText("");
        editAsistencia.setText("");
        editEmpleadoId.setText("");
        editCapacitacionId.setText("");
    }
}
