package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    //Agregen las tablas y las activities de las tablas
    String[] menu={"Tabla AreaInteres","Tabla EntidadCapacitadora","Tabla Diplomado","Tabla Area Diplomado","Tabla Capacitador", "Tabla Empleado","Tabla Solicitud","Tabla Facultad","Tabla Ubicacion","Tabla TipoUbicacion","Tabla Local","Tabla Asistencia de empleado","Tabla Dia","01 Tabla Horario","Tabla capacitacion"};
    String[] activities={"AreaInteresMenuActivity","EntidadCapacitadoraMenuActivity","DiplomadoMenuActivity","AreaDiplomadoMenuActivity","CapacitadorMenuActivity","EmpleadoMenuActivity","SolicitudMenuActivity","FacultadMenuActivity","UbicacionMenuActivity","TipoUbicacionMenuActivity","LocalMenuActivity" ,"AsistenciaEmpleadoMenuActivity","DiaMenuActivity","CZ13016MenuHorarioActivity","CZ13016CapacitacionMenuActivity"};


    ControlBDProyecto BDhelper;
    //variable de la sesion de usuario
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
        BDhelper=new ControlBDProyecto(this);

        //sirve para manejar el id de la sesion del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idsesion");
            //The key argument here must match that used in the other activity
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String idopcionS = "000";
        switch (position) {
            case 0:
                idopcionS = "010";
                break;
            case 1:
                idopcionS = "020";
                break;
            case 2:
                idopcionS = "030";
                break;
            case 3:
                idopcionS = "040";
                break;
            case 4:
                idopcionS = "050";
                break;
            case 5:
                idopcionS = "060";
                break;
            case 6:
                idopcionS = "070";
                break;
            case 7:
                idopcionS = "080";
                break;
            case 8:
                idopcionS = "090";
                break;
            case 9:
                idopcionS = "100";
                break;
            case 10:
                idopcionS = "110";
                break;
            case 11:
                idopcionS = "120";
                break;
            case 12:
                idopcionS = "130";
                break;
            case 13:
                idopcionS = "140";
                break;
            case 14:
                idopcionS = "150";
                break;

            default:
                /*temporal
                String nombreValue = activities[position];

                try {
                    Class<?> clase = Class.forName("com.example.proyecto_pdm_g10." + nombreValue);

                    Intent inte = new Intent(this, clase);

                    inte.putExtra("idsesion", idsesion);
                    this.startActivity(inte);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
                break;
        }
        //Verificacion usuario
        BDhelper.abrir();
        Usuario usuario = BDhelper.consultarUsuario(idsesion);
        //AQUI SE AGREGA EL CODIGO DE ACCESO, SOLO CAMBIAR EL idopcion al numero que sea
        AccesoUsuario accesoUsuario = BDhelper.consultarAccesoUsuario(usuario.getIdUsuario(), idopcionS);
        BDhelper.cerrar();
        //fin verificacion

        if (accesoUsuario == null) {
            Toast.makeText(this, "Disculpe Usted no tiene permisos para acceder a esa seccion", Toast.LENGTH_SHORT).show();
        } else {

            String nombreValue = activities[position];
            String miOpcion = nombreValue.substring(0, 7);
            Toast.makeText(this, miOpcion, Toast.LENGTH_SHORT).show();

            try {
                    Class<?> clase = null;
                    if(idopcionS.equals("140"))
                    {
                        clase = Class.forName("com.example.proyecto_pdm_g10.cz13016_activities.CZ13016MenuHorarioActivity"  );
                    }
                    else if(idopcionS.equals("150"))
                    {
                         clase = Class.forName("com.example.proyecto_pdm_g10.cz13016_activities.CZ13016CapacitacionMenuActivity" );
                    }else {
                         clase = Class.forName("com.example.proyecto_pdm_g10." + nombreValue);
                    }
                    Intent inte = new Intent(this, clase);
                    inte.putExtra("idsesion", idsesion);
                    this.startActivity(inte);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}