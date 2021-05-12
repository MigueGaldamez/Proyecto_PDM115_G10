package com.example.proyecto_pdm_g10;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
public class UbicacionMenuActivity extends ListActivity
{
    String[] menu={"Insertar Registro","Eliminar Registro","Consultar Registro", "Actualizar Registro"};
    String[] activities={"UbicacionInsertarActivity","UbicacionEliminarActivity","UbicacionConsultarActivity", "UbicacionActualizarActivity"};
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        listView.setBackgroundColor(Color.rgb(64, 0, 128));
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        String nombreValue=activities[position];
        l.getChildAt(position).setBackgroundColor(Color.rgb(255, 128, 0));
        try
        {
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