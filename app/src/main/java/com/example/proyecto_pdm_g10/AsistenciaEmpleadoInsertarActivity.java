package com.example.proyecto_pdm_g10;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsistenciaEmpleadoInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAsistenciaEmpleado;
    EditText editAsistencia;
    EditText editEmpleadoId;
    EditText editCapacitacionId;

    //   ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    //  String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_empleado_insertar);
        helper = new ControlBDProyecto(this);
        editIdAsistenciaEmpleado = (EditText) findViewById(R.id.editIdAsistenciaEmpleado);
        editAsistencia = (EditText) findViewById(R.id.editAsistencia);
        editEmpleadoId = (EditText) findViewById(R.id.editEmpleadoId);
        editCapacitacionId = (EditText) findViewById(R.id.editCapacitacionId);

    }
    public void insertarAsistenciaEmpleado(View v) {
        String idAsistenciaEmpleado = editIdAsistenciaEmpleado.getText().toString();
        String asistencia = editAsistencia.getText().toString();
        String empleadoId = editEmpleadoId.getText().toString();
        String capacitacionId = editCapacitacionId.getText().toString();

        String regInsertados;
        AsistenciaEmpleado asistenciaEmpleado=new AsistenciaEmpleado();
        asistenciaEmpleado.setIdAsistenciaEmpleado(idAsistenciaEmpleado);
        asistenciaEmpleado.setAsistencia(asistencia);
        asistenciaEmpleado.setEmpleadoId(empleadoId);
        asistenciaEmpleado.setCapacitacionId(capacitacionId);

        helper.abrir();
        regInsertados=helper.insertar(asistenciaEmpleado);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editIdAsistenciaEmpleado.setText("");
        editAsistencia.setText("");
        editEmpleadoId.setText("");
        editCapacitacionId.setText("");

    }


}
