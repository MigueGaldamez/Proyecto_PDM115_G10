package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EmpleadoConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdEmpleado;
    EditText editNombreEmpleado;
    EditText editApellidoEmpleado;
    EditText editProfesion;
    EditText editCargo;
    EditText editSolicitudes;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_consultar);
        helper = new ControlBDProyecto(this);
        editIdEmpleado = (EditText) findViewById(R.id.editIdEmpleado);
        editNombreEmpleado = (EditText) findViewById(R.id.editNombreEmpleado);
        editApellidoEmpleado = (EditText) findViewById(R.id.editApellidoEmpleado);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editCargo = (EditText) findViewById(R.id.editCargo);
        editSolicitudes = (EditText) findViewById(R.id.editSolicitudes);
    }

    public void consultarEmpleado(View v) {
        helper.abrir();
        Empleado empleado = helper.consultarEmpleado(editIdEmpleado.getText().toString());
        helper.cerrar();
        if(empleado == null)
            Toast.makeText(this, "Empleado con codigo" + editIdEmpleado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombreEmpleado.setText(empleado.getNombreEmpleado());
            editApellidoEmpleado.setText(empleado.getApellidoEmpleado());
            editProfesion.setText(empleado.getProfesion());
            editCargo.setText(empleado.getCargo());
            editSolicitudes.setText(empleado.getSolicitudes().toString());

        }
    }
    public void limpiarTexto(View v) {
        editIdEmpleado.setText("");
        editNombreEmpleado.setText("");
        editApellidoEmpleado.setText("");
        editProfesion.setText("");
        editCargo.setText("");
        editSolicitudes.setText("");
    }
}
