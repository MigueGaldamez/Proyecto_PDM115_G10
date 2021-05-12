package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaDiplomadoInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;
    EditText editNombre;
    EditText editDescripcion;
    EditText editIdDiplomado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_insertar);

        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
    }
    public void insertarAreaDiplomado(View v) {
        String idAreaDiplomado = editIdAreaDiplomado.getText().toString();
        String nombre = editNombre.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String idDiplomado = editIdDiplomado.getText().toString();
        String regInsertados;

        AreaDiplomado areaDiplomado=new AreaDiplomado();
        areaDiplomado.setIdAreaDiplomado(idAreaDiplomado);
        areaDiplomado.setNombre(nombre);
        areaDiplomado.setDescripcion(descripcion);
        areaDiplomado.setIdDiplomado(idDiplomado);
        helper.abrir();
        regInsertados=helper.insertar(areaDiplomado);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editIdAreaDiplomado.setText("");
    }

}