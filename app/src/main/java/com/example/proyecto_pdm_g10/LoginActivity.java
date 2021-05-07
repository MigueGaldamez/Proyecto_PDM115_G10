package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    ControlBDProyecto BDhelper;
    EditText editUsuario;
    EditText editClave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BDhelper = new ControlBDProyecto(this);
        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editClave = (EditText) findViewById(R.id.editClave);
    }
    public void consultarUsuario(View v) {
        String main = "MainActivity";
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(editUsuario.getText().toString(), editClave.getText().toString());
        BDhelper.cerrar();
        if(usuario == null)
            Toast.makeText(this, "Usuario no registrado",
                    Toast.LENGTH_LONG).show();

        else{
            Toast.makeText(this, "Logeado", Toast.LENGTH_LONG).show();
            try {
                //editNotafinal.setText(String.valueOf(nota.getNotafinal()));

                Class<?> clase = Class.forName("com.example.proyecto_pdm_g10." + main);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            } catch(ClassNotFoundException e){
            e.printStackTrace();
            }
        }
    }
    public void limpiarTexto(View v) {
        editUsuario.setText("");
        editClave.setText("");

    }
    public void LlenarBaseUsuario(View v){
        BDhelper.abrir();
        String tost=BDhelper.llenarBDUsuario();
        BDhelper.cerrar();
        Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
    }

}