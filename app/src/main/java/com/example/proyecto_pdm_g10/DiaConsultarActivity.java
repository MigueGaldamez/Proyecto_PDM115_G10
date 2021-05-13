package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiaConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDia;
    EditText editNomDia;
    EditText editFecha;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_consultar);
        helper = new ControlBDProyecto(this);
        editIdDia = (EditText) findViewById(R.id.editIdDia);
        editNomDia = (EditText) findViewById(R.id.editNomDia);
        editFecha = (EditText) findViewById(R.id.editFecha);

        //INICIO VALIDACION DE ROL
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idsesion");
        }
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(idsesion);
        //AQUI SE AGREGA EL CODIGO DE ACCESO, SOLO CAMBIAR EL idopcion al numero que sea
        AccesoUsuario accesoUsuario = BDhelper.consultarAccesoUsuario(usuario.getIdUsuario(),"044");
        BDhelper.cerrar();
        if(accesoUsuario == null)
        {
            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10.DiplomadoMenuActivity");
                Intent inte = new Intent(this,clase);
                inte.putExtra("idsesion",idsesion);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            Toast.makeText(this, "Usted no tiene permisos para acceder a esa seccion", Toast.LENGTH_SHORT).show();
        }
        //FIN VERIFICACION
    }

    public void consultarDia(View v) {
        helper.abrir();
        Dia dia = helper.consultarDia(editIdDia.getText().toString());
        helper.cerrar();
        if(dia == null)
            Toast.makeText(this, "Codigo del Dia" + editIdDia.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNomDia.setText(dia.getNomDia());
            editFecha.setText(dia.getFecha());
        }
    }
    public void limpiarTexto(View v) {
        editIdDia.setText("");
        editNomDia.setText("");
        editFecha.setText("");
    }
}