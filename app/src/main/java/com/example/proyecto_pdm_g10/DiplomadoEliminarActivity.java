package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiplomadoEliminarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/diplomado/ws_diplomado_delete.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/diplomado/ws_diplomado_delete.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_eliminar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
    }
    public void eliminarDiplomado(View v){
        String regEliminadas;
        Diplomado diplomado=new Diplomado();
        diplomado.setIdDiplomado(editIdDiplomado.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(diplomado);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void eliminarDiplomadoExterno(View v)
    {
        String codigo = editIdDiplomado.getText().toString();
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_diplomado=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_diplomado=" + codigo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }

    }
}