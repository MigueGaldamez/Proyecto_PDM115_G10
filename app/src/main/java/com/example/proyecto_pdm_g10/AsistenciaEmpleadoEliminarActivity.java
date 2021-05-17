package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsistenciaEmpleadoEliminarActivity extends AppCompatActivity {
    ControlBDProyecto helper;
    EditText editIdAsistenciaEmpleado;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_empleado_eliminar);
        helper = new ControlBDProyecto(this);
        editIdAsistenciaEmpleado = (EditText) findViewById(R.id.editIdAsistenciaEmpleado);

    }
    public void eliminarAsistenciaEmpleado(View v){
        String regEliminadas;
        AsistenciaEmpleado asistenciaEmpleado=new AsistenciaEmpleado();
        asistenciaEmpleado.setIdAsistenciaEmpleado(editIdAsistenciaEmpleado.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(asistenciaEmpleado);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}
