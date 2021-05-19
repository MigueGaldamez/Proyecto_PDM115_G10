package com.example.proyecto_pdm_g10;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmpleadoInsertarActivity extends Activity {
	ControlBDProyecto helper;
    EditText editIdEmpleado;
    EditText editNombreEmpleado;
    EditText editApellidoEmpleado;
    EditText editProfesion;
    EditText editCargo;

//   ControlBDProyecto BDhelper = new ControlBDProyecto(this);
  //  String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_insertar);
        helper = new ControlBDProyecto(this);
        editIdEmpleado = (EditText) findViewById(R.id.editIdEmpleado);
        editNombreEmpleado = (EditText) findViewById(R.id.editNombreEmpleado);
        editApellidoEmpleado = (EditText) findViewById(R.id.editApellidoEmpleado);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editCargo = (EditText) findViewById(R.id.editCargo);
    }
    public void insertarEmpleado(View v) {
        String idEmpleado = editIdEmpleado.getText().toString();
        String nombreEmpleado = editNombreEmpleado.getText().toString();
        String apellidoEmpleado = editApellidoEmpleado.getText().toString();
        String profesion = editProfesion.getText().toString();
        String cargo = editCargo.getText().toString();
        String regInsertados;
        Empleado empleado=new Empleado();
        empleado.setIdEmpleado(idEmpleado);
        empleado.setNombreEmpleado(nombreEmpleado);
        empleado.setApellidoEmpleado(apellidoEmpleado);
        empleado.setProfesion(profesion);
        empleado.setCargo(cargo);
        helper.abrir();
        regInsertados=helper.insertar(empleado);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editIdEmpleado.setText("");
        editNombreEmpleado.setText("");
        editApellidoEmpleado.setText("");
        editProfesion.setText("");
        editCargo.setText("");
    }


}
