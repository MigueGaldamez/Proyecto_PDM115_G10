package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AreaDiplomadoMenuActivity extends ListActivity {
    String[] menu={"Insertar Registro","Eliminar Registro","Consultar Registro", "Actualizar Registro"};
    String[] activities={"AreaDiplomadoInsertarActivity","AreaDiplomadoEliminarActivity","AreaDiplomadoConsultarActivity", "AreaDiplomadoActualizarActivity"};


    ControlBDProyecto BDhelper= new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setBackgroundColor(Color.rgb(0, 0, 255));
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);

        //INICIO VALIDACION DE ROL
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idsesion");
        }
        //FIN VERIFICACION
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        String idopcionS = "000";
        switch(position) {
            case 0:  idopcionS = "051";
                break;
            case 1:idopcionS = "053";
                break;
            case 2:idopcionS = "054";
                break;
            case 3:idopcionS = "052";
                break;
            default:
                break;
        }
        //Verificacion usuario
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(idsesion);
        AccesoUsuario accesoUsuario = BDhelper.consultarAccesoUsuario(usuario.getIdUsuario(),idopcionS);
        BDhelper.cerrar();
        //fin verificacion
        if(accesoUsuario == null)
        {
            Toast.makeText(this, "Disculpe Usted no tiene permisos para acceder a esa seccion", Toast.LENGTH_SHORT).show();
        }
        else {
            String nombreValue=activities[position];


            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10."+nombreValue);
                Intent inte = new Intent(this,clase);
                inte.putExtra("idsesion",idsesion);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }

    }
}