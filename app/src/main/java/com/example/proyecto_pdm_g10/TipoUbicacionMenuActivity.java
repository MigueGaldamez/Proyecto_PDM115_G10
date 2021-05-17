package com.example.proyecto_pdm_g10;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TipoUbicacionMenuActivity extends ListActivity
{
    String[] menu={"Insertar Registro","Eliminar Registro","Consultar Registro", "Actualizar Registro"};
    String[] activities={"TipoUbicacionInsertarActivity","TipoUbicacionEliminarActivity","TipoUbicacionConsultarActivity", "TipoUbicacionActualizarActivity"};
    ControlBDProyecto BDhelper= new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        listView.setBackgroundColor(Color.rgb(92, 27, 108));
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);

        //INICIO VALIDACION DE ROL
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            idsesion = extras.getString("idsesion");
            //The key argument here must match that used in the other activity
        }
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(idsesion);
        //AQUI SE AGREGA EL CODIGO DE ACCESO
        AccesoUsuario accesoUsuario = BDhelper.consultarAccesoUsuario(usuario.getIdUsuario(),"010");
        BDhelper.cerrar();
        if(accesoUsuario == null)
        {

            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10.MainActivity");
                Intent inte = new Intent(this,clase);
                inte.putExtra("idsesion",idsesion);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            Toast.makeText(this, "Usted no tiene permisos para acceder a esa seccion", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //tiene permisos
            //Toast.makeText(this, "Bi", Toast.LENGTH_SHORT).show();
        }
        //FIN VERIFICACION
    }
    @Override
    protected void onListItemClick(ListView l,View v,int position,long id)
    {
        super.onListItemClick(l, v, position, id);

        String nombreValue=activities[position];

        l.getChildAt(position).setBackgroundColor(Color.rgb(92, 27, 110));

        try{

            Class<?> clase=Class.forName("com.example.proyecto_pdm_g10."+nombreValue);
            Intent inte = new Intent(this,clase);
            this.startActivity(inte);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}