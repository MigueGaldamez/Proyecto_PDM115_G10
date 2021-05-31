package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CapacitadorActualizarActivity extends Activity {

    ControlBDProyecto helper;
    EditText editIdCapacitador;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editProfesion;
    Spinner spinner,spinnerExterno;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/capacitador/ws_capacitador_update.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/capacitador/ws_capacitador_update.php";

    //PARA LOS DROPDOWN
    private final String urlLocal2 = "http://192.168.1.5/ServiciosWeb%20PDM/entidadCapacitadora/ws_entidadCapacitadora_todo.php";
    private final String urlHostingGratuito2 = "https://proyectopdm-g10.000webhostapp.com/entidadCapacitadora/ws_entidadCapacitadora_todo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitador_actualizar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
        editNombres = (EditText) findViewById(R.id.editNombres);
        editApellidos = (EditText) findViewById(R.id.editApellidos);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        spinner = (Spinner)findViewById(R.id.editIdEntidadCapacitadora);
        spinnerExterno = (Spinner)findViewById(R.id.editIdEntidadCapacitadora_externa);

        helper.abrir();
        String[] campos = {"codigo","nombre"};
        helper.consultarListaObjeto(4,"entidadCapacitadora",campos);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinner.setAdapter(adaptador);
        helper.cerrar();
        ArrayList<String> listaObjeto_externo =ControladorServicio.consultarListaObjeto_Externo(1,urlHostingGratuito2,this);
        ArrayAdapter<CharSequence> adaptador2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaObjeto_externo);
        spinnerExterno.setAdapter(adaptador2);
    }
    public void actualizarCapacitador(View v) {
        Capacitador capacitador = new Capacitador();
        capacitador.setIdCapacitador(editIdCapacitador.getText().toString());
        capacitador.setNombres(editNombres.getText().toString());
        capacitador.setApellidos(editApellidos.getText().toString());
        capacitador.setTelefono(editTelefono.getText().toString());
        capacitador.setCorreo(editCorreo.getText().toString());
        capacitador.setProfesion(editProfesion.getText().toString());
        String idF = spinner.getSelectedItem().toString();
        String[] entidadCapacitadoraId = idF.split(" - ");
        capacitador.setIdEntidadCapacitadora(entidadCapacitadoraId[0].trim());

        helper.abrir();
        String estado = helper.actualizar(capacitador);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void actualizarCapacitadorExterno(View v)
    {
        String idCapacitador = editIdCapacitador.getText().toString();
        String nombres = editNombres.getText().toString();
        String apellidos = editApellidos.getText().toString();
        String telefono = editTelefono.getText().toString();
        String correo = editCorreo.getText().toString();
        String profesion = editProfesion.getText().toString();

        //APARA EL HOSTING
        String idFE = spinnerExterno.getSelectedItem().toString();
        String[] entidadCapacitadoraId_E = idFE.split(" - ");

        //PARA EL LOCAL
        /*String idF = spinner.getSelectedItem().toString();
        String[] entidadCapacitadoraId = idF.split(" - ");*/
        //profesion=Estudiante&telefono=73356798&correo=migue.galdamez@hotmail.com
        String url = null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_capacitador=" + idCapacitador + "&id_entidad=" + entidadCapacitadoraId_E[0].trim() + "&nombres="
                        + nombres +"&apellidos="+apellidos+"&profesion="+profesion+"&telefono="+telefono + "&correo="+correo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+  "?id_capacitador=" + idCapacitador + "&id_entidad=" + entidadCapacitadoraId_E[0].trim() + "&nombres="
                        + nombres +"&apellidos="+apellidos+"&profesion="+profesion+"&telefono="+telefono + "&correo="+correo;
                ControladorServicio.ejecutarConsulta(url, this);
                break;
        }

    }
    public void limpiarTexto(View v) {
        editIdCapacitador.setText("");
        editNombres.setText("");
        editApellidos.setText("");
        editTelefono.setText("");
        editCorreo.setText("");
        editProfesion.setText("");
        spinner.setSelection(0);
    }
}