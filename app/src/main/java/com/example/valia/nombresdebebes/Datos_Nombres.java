package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rudyng on 1/24/2022.
 */

public class Datos_Nombres extends AppCompatActivity {


    ArrayList<PersonaSign> personas;
    android.widget.SearchView buscador;
    RecyclerView recyclePersonas;
    AdaptadorListaNombresSignListaDatos adaptadorListaNombres;

    DATOS mService1;

    boolean ejecutado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_nombres);


        Intent in = new Intent(this, DATOS.class);
        this.bindService(in, mConnection1, Context.BIND_AUTO_CREATE);

        personas=new ArrayList<>();

        buscador = (android.widget.SearchView) findViewById(R.id.id_buscar_origen);
        recyclePersonas = (RecyclerView) findViewById(R.id.id_recycler_origen);

        //Carga personas de la base de datos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ejecutado){
                    cargar();
                    ejecutado=true;
                }
            }
        },100);

        getSupportActionBar().setTitle(getString(R.string.datos_nombres));

    }

    public void cargar(){
        personas=mService1.getPersonasSign();
        guardar_en_lista1(personas);
    }


    public void guardar_en_lista1(final ArrayList<PersonaSign> listaDpersonas) {

        //METODO QUE GUARDA EN LA LISTA
        selectLista(listaDpersonas);

        //QUE FUNCIONE LA BUSQUEDA
        buscador.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<PersonaSign> listaFiltrada= adaptadorListaNombres.getFiltrerSign(listaDpersonas,newText);
                    selectLista(listaFiltrada);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


    //INICIAR EL RECYCLE Y HACER ALGO DE ACUERDO A LO QUE SELECCIONE DE LA LISTA
    private void selectLista(final ArrayList<PersonaSign> listaDpersonas){

        recyclePersonas.setLayoutManager(new LinearLayoutManager(this));
        adaptadorListaNombres= new AdaptadorListaNombresSignListaDatos(listaDpersonas);
        adaptadorListaNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Datos_Nombres1.class);
                i.putExtra("NOMBRE_SELECCIONADO", listaDpersonas.get(recyclePersonas.getChildAdapterPosition(view)).getNombre());
                startActivity(i);
            }
        });

        recyclePersonas.setAdapter(adaptadorListaNombres);

    }


    private ServiceConnection mConnection1 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DATOS.LocalBinder binder = (DATOS.LocalBinder) service;
            mService1 = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

}
