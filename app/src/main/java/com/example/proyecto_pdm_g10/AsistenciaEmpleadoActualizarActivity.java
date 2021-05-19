package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsistenciaEmpleadoActualizarActivity extends Activity {
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
        setContentView(R.layout.activity_asistencia_empleado_actualizar);

        helper = new ControlBDProyecto(this);
        editIdAsistenciaEmpleado = (EditText) findViewById(R.id.editIdAsistenciaEmpleado);
        editAsistencia = (EditText) findViewById(R.id.editAsistencia);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);

    }
    public void actualizarAsistenciaEmpleado(View v) {
        AsistenciaEmpleado asistenciaEmpleado=new AsistenciaEmpleado();
        asistenciaEmpleado.setIdAsistenciaEmpleado(editIdAsistenciaEmpleado.getText().toString());
        asistenciaEmpleado.setAsistencia(editAsistencia.getText().toString());
        asistenciaEmpleado.setEmpleadoId(editEmpleadoId.getText().toString());
        asistenciaEmpleado.setCapacitacionId(editCapacitacionId.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(asistenciaEmpleado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdAsistenciaEmpleado.setText("");
        editAsistencia.setText("");
        editEmpleadoId.setText("");
        editCapacitacionId.setText("");
    }
}