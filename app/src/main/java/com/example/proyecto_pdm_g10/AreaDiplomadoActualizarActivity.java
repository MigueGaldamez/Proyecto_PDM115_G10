package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AreaDiplomadoActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;
    EditText editNombre;
    EditText editDescripcion;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_actualizar);
        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);

        spinner = (Spinner)findViewById(R.id.editIdDiplomado);
        helper.abrir();
        String[] campos = {"idDiplomado","titulo"};
        helper.consultarListaObjeto(5,"diplomado",campos);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinner.setAdapter(adaptador);
        helper.cerrar();
    }
    public void actualizarAreaDiplomado(View v) {
        AreaDiplomado areaDiplomado = new AreaDiplomado();
        areaDiplomado.setIdAreaDiplomado(editIdAreaDiplomado.getText().toString());
        areaDiplomado.setNombre(editNombre.getText().toString());
        areaDiplomado.setDescripcion(editDescripcion.getText().toString());
        String idF = spinner.getSelectedItem().toString();
        String[] diploadoID = idF.split(" - ");
        areaDiplomado.setIdDiplomado(diploadoID[0].trim());
        helper.abrir();
        String estado = helper.actualizar(areaDiplomado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdAreaDiplomado.setSelection(0);
        editNombre.setText("");
        editDescripcion.setText("");
        editIdAreaDiplomado.setText("");
    }
}