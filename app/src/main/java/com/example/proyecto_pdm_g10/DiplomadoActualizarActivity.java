package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiplomadoActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;
    EditText editTitulo;
    EditText editDescripcion;
    EditText editCapacidades;
    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/diplomado/ws_diplomado_update.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/diplomado/ws_diplomado_update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_actualizar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editCapacidades = (EditText) findViewById(R.id.editCapacidades);
    }
    public void actualizarDiplomado(View v) {
        Diplomado diplomado = new Diplomado();
        diplomado.setIdDiplomado(editIdDiplomado.getText().toString());
        diplomado.setTitulo(editTitulo.getText().toString());
        diplomado.setDescripcion(editDescripcion.getText().toString());
        diplomado.setCapacidades(editCapacidades.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(diplomado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void actualizarDiplomadoExterno(View v)
    {
        String idDiplomado = editIdDiplomado.getText().toString();
        String titulo = editTitulo.getText().toString();
        String descripcion = editDescripcion.getText().toString();
        String capacidades = editCapacidades.getText().toString();
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_diplomado=" + idDiplomado + "&titulo="
                        + titulo + "&descripcion=" + descripcion +"&capacidades="+capacidades;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_diplomado=" + idDiplomado + "&titulo="
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