package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmpleadoActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdEmpleado;
    EditText editNombreEmpleado;
    EditText editApellidoEmpleado;
    EditText editProfesion;
    EditText editCargo;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_actualizar);

        helper = new ControlBDProyecto(this);
        editIdEmpleado = (EditText) findViewById(R.id.editIdEmpleado);
        editNombreEmpleado = (EditText) findViewById(R.id.editNombreEmpleado);
        editApellidoEmpleado = (EditText) findViewById(R.id.editApellidoEmpleado);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editCargo = (EditText) findViewById(R.id.editCargo);
    }
    public void actualizarEmpleado(View v) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(editIdEmpleado.getText().toString());
        empleado.setNombreEmpleado(editNombreEmpleado.getText().toString());
        empleado.setApellidoEmpleado(editApellidoEmpleado.getText().toString());
        empleado.setProfesion(editProfesion.getText().toString());
        empleado.setCargo(editCargo.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(empleado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdEmpleado.setText("");
        editNombreEmpleado.setText("");
        editApellidoEmpleado.setText("");
        editProfesion.setText("");
        editCargo.setText("");
    }
}