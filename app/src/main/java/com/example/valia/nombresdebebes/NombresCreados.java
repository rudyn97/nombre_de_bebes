package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Valia on 27/08/2021.
 */

public class NombresCreados extends AppCompatActivity {

    DATOS mService;
    ArrayList<Persona> personas;
    ImageView imageViewFotoBebe;
    ListView idLista1;
    ListView idLista2;
    LinearLayout idLinearListas;
    String sexo;
    String letraInicial;
    int cantidadDletras;
    boolean compuesto;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nombres_creados);

        Bundle datos = getIntent().getExtras();

        idLinearListas = (LinearLayout) findViewById(R.id.idLinearListas);
        imageViewFotoBebe = (ImageView) findViewById(R.id.imageViewFotoBebe);
        idLista1 = (ListView)findViewById(R.id.idLista1);
        idLista2 = (ListView)findViewById(R.id.idLista2);

        Intent in = new Intent(this, DATOS.class);
        bindService(in, mConnection, Context.BIND_AUTO_CREATE);

        sexo=datos.getString("sexo");
        letraInicial = datos.getString("letraI");
        cantidadDletras = datos.getInt("cantL");
        compuesto = datos.getBoolean("compuesto");

        /*Toast.makeText(this, ""+sexo, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+letraInicial, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+cantidadDletras, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+compuesto, Toast.LENGTH_SHORT).show();*/

        ponerFoto(sexo);

        personas = new ArrayList<>();
        cargarNombres();

        getSupportActionBar().setTitle(getString(R.string.nombres_creados));

    }

    private void cargarNombres(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                personas = mService.getPersonas();
                ponerAlista(personas,letraInicial,sexo,compuesto,cantidadDletras);

                Animation animation1 = AnimationUtils.loadAnimation(NombresCreados.this, R.anim.aparece_up);
                idLinearListas.startAnimation(animation1);

            }
        },50);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ponerFoto(String sexo){
        if (sexo.equals(""+getString(R.string.femenino))){
            imageViewFotoBebe.setImageDrawable(getDrawable(R.drawable.ninas));
        }
        if (sexo.equals(""+getString(R.string.masculino))){
            imageViewFotoBebe.setImageDrawable(getDrawable(R.drawable.ninos));
        }

    }

    private void ponerAlista(ArrayList<Persona> personas,
                             String inicial,
                             String sexo,
                             boolean compuesto,
                             int cantidadLetras){

        ArrayList<String> devolver1 = new ArrayList<>();
        ArrayList<String> devolver2 = new ArrayList<>();

        ArrayList<Persona> personas1;
        personas1 = buscarPersonas(personas,inicial,sexo,compuesto,cantidadLetras);


        for (int i=0;i<(personas1.size()/2);i++){
            devolver1.add(personas1.get(i).getNombre());
        }
        for (int i=(personas1.size()/2);i<personas1.size();i++){
            devolver2.add(personas1.get(i).getNombre());
        }

        ArrayAdapter<String> lista1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,devolver1);
        ArrayAdapter<String> lista2=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,devolver2);

        idLista1.setAdapter(lista1);
        idLista2.setAdapter(lista2);


    }

    public static ArrayList<Persona> buscarPersonas(ArrayList<Persona> personas,
                                                  String inicial,
                                                  String sexo,
                                                  boolean compuesto,
                                                  int cantidadLetras){
        ArrayList<Persona> resultado = new ArrayList<>();


        int index = personas.size();
        for(int i = 0; i<index; i++){
            if (cantidadLetras!=0){
                if (cantidadLetras==personas.get(i).getNombre().length()){
                    if (inicial.toLowerCase().equals(""+personas.get(i).getNombre().toLowerCase().charAt(0))){
                        if (sexo.toLowerCase().equals(personas.get(i).getSexo().toLowerCase())){
                            if (compuesto){
                                if(personas.get(i).getNombre().trim().contains(" ")){    //Si es compuesto
                                    resultado.add(personas.get(i));
                                }
                            }else{
                                if(!personas.get(i).getNombre().trim().contains(" ")){  //Si no es compuesto
                                    resultado.add(personas.get(i));
                                }
                            }
                        }
                    }
                }
            }else {
                if (inicial.toLowerCase().equals(""+personas.get(i).getNombre().toLowerCase().charAt(0))){
                    if (sexo.toLowerCase().equals(personas.get(i).getSexo().toLowerCase())){
                        if (compuesto){
                            if(personas.get(i).getNombre().trim().contains(" ")){    //Si es compuesto
                                resultado.add(personas.get(i));
                            }
                        }else{
                            if(!personas.get(i).getNombre().trim().contains(" ")){  //Si no es compuesto
                                resultado.add(personas.get(i));
                            }
                        }
                    }
                }

            }


            /*if(cantidadLetras!=0 && personas.get(i).getNombre().length()!=cantidadLetras)
                continue;

            if(personas.get(i).getNombre().toLowerCase().charAt(0)==inicial.toLowerCase().charAt(0)){      //Si coincide la inicial
                if(personas.get(i).getSexo().equalsIgnoreCase(sexo)){    //Si coincide el sexo
                    if(compuesto){
                        if(personas.get(i).getNombre().trim().contains(" ")){    //Si es compuesto
                            resultado.add(personas.get(i).getNombre());
                        }
                    }else
                    if(!personas.get(i).getNombre().trim().contains(" ")){  //Si no es compuesto
                        resultado.add(personas.get(i).getNombre());
                    }
                }
            }*/
        }

        return resultado;
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DATOS.LocalBinder binder = (DATOS.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

}
