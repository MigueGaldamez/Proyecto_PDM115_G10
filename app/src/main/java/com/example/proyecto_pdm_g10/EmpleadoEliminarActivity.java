package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmpleadoEliminarActivity extends AppCompatActivity {
    ControlBDProyecto helper;
    EditText editIdEmpleado;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_eliminar);
        helper = new ControlBDProyecto(this);
        editIdEmpleado = (EditText) findViewById(R.id.editIdEmpleado);

    }

    public void eliminarEmpleado(View v) {
        String regEliminadas;
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(editIdEmpleado.getText().toString());
        helper.abrir();
        regEliminadas = helper.eliminar(empleado);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();

    }
}