package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiplomadoInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;
    EditText editTitulo;
    EditText editDescripcion;
    EditText editCapacidades;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_insertar);
        helper = new ControlBDProyecto(this);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editCapacidades = (EditText) findViewById(R.id.editCapacidades);
    }
    public void insertarDiplomado(View v) {
        String idDiplomado = editIdDiplomado.getText().toString();
        String titulo = editTitulo.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String capacidades = editCapacidades.getText().toString();
        String regInsertados;
        Diplomado diplomado=new Diplomado();
        diplomado.setIdDiplomado(idDiplomado);
        diplomado.setTitulo(titulo);
        diplomado.setDescripcion(descripcion);
        diplomado.setCapacidades(capacidades);
        helper.abrir();
        regInsertados=helper.insertar(diplomado);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }

    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editTitulo.setText("");
        editDescripcion.setText("");
        editCapacidades.setText("");
    }
}