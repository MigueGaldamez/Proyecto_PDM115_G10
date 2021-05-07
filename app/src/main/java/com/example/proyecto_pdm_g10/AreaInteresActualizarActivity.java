package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaInteresActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_interes_actualizar);

        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        //INICIO VALIDACION DE ROL
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idsesion");
        }
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(idsesion);
        //AQUI SE AGREGA EL CODIGO DE ACCESO
        AccesoUsuario accesoUsuario = BDhelper.consultarAccesoUsuario(usuario.getIdUsuario(),"012");
        BDhelper.cerrar();
        if(accesoUsuario == null)
        {
            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10.AreaInteresMenuActivity");
                Intent inte = new Intent(this,clase);
                inte.putExtra("idsesion",idsesion);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            Toast.makeText(this, "Usted no tiene permisos para acceder a esa seccion", Toast.LENGTH_SHORT).show();

        }
        else {
        }//FIN VERIFICACION
    }
    public void actualizarAreaInteres(View v) {
        AreaInteres areaInteres = new AreaInteres();
        areaInteres.setCodigo(editCodigo.getText().toString());
        areaInteres.setNombre(editNombre.getText().toString());
        areaInteres.setDescripcion(editDescripcion.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(areaInteres);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");

    }

}