package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiplomadoInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;
    EditText editTitulo;
    EditText editDescripcion;
    EditText editCapacidades;
    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/diplomado/ws_diplomado_insert.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/diplomado/ws_diplomado_insert.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_insertar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
    public void insertarDiplomadoExterno(View v)
    {
        String idDiplomado = editIdDiplomado.getText().toString();
        String titulo = editTitulo.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String capacidades = editCapacidades.getText().toString();
        String url = null;
      //id_diplomado=DIPLO2&titulo=Titulo1&descripcion=Descripcion1&capacidades=Configuracion%20de%20DNS

        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_diplomado=" + idDiplomado + "&titulo="
                        + titulo + "&descripcion=" + descripcion +"&capacidades="+capacidades;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+  "?id_diplomado=" + idDiplomado + "&titulo="
                        + titulo + "&descripcion=" + descripcion +"&capacidades="+capacidades;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }
    }

    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editTitulo.setText("");
        editDescripcion.setText("");
        editCapacidades.setText("");
    }
}