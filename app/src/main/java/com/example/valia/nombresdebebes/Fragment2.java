package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 24/12/2019.
 */

public class Fragment2 extends Fragment{
    Spinner spinner;

    //QUE COJA ESTO DE ACUERDO AL IDIOMA
    //String[] count = {"Uruguay","España","Estados Unidos","Italia"};

    int[] images={R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5};

    ArrayList<Persona> personas;
    android.widget.SearchView buscador;
    RecyclerView recyclePersonas;
    AdaptadorListaNombres adaptadorListaNombres;

    DATOS mService;
    //boolean ejecutado = false;

    ArrayList<String> paises;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment2 newInstance(int sectionNumber) {
        Fragment2 fragment = new Fragment2();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public Fragment2() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment1,container,false);

        Intent in = new Intent(vista.getContext(), DATOS.class);
        vista.getContext().bindService(in, mConnection, Context.BIND_AUTO_CREATE);

        paises = new ArrayList<>();
        paises.add(""+getString(R.string.uruguay));
        paises.add(""+getString(R.string.españa));
        paises.add(""+getString(R.string.eeuu));
        paises.add(""+getString(R.string.italia));
        paises.add(""+getString(R.string.cuba));



        //PARA EL SPINNER
        spinner = (Spinner) vista.findViewById(R.id.spinner1);
        AdapterSpinner adapter = new AdapterSpinner(getContext(),paises,images);
        spinner.setAdapter(adapter);


        //PARA LA BUSQUEDA Y LA LISTA
        buscador = (android.widget.SearchView) vista.findViewById(R.id.buscar1);
        recyclePersonas = (RecyclerView) vista.findViewById(R.id.recycle);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //PARA FILTRAR DATOS DE ACUERDO A LA SELECCION DEL SPINNER
                //Persona p;
                /*if (!ejecutado){
                    cargar();
                    ejecutado=true;
                }*/

                cargar();
                ArrayList<Persona> listaPersonas= new ArrayList<Persona>();

                mService.cargarFavoritos(view.getContext());
                if (i==0){
                    //BDd.cargarFavoritos(view.getContext());

                    for (int i1=0;i1<personas.size();i1++){
                        if (personas.get(i1).getPais().contains("Uruguay")){
                            //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                            listaPersonas.add(personas.get(i1));

                        }
                    }
                }else{
                    if (i==1){
                        //BDd.cargarFavoritos(view.getContext());
                        //mService.cargarFavoritos(view.getContext());
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getPais().contains("España")){
                                //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                    }else{
                        if (i==2) {
                            //BDd.cargarFavoritos(view.getContext());
                            //mService.cargarFavoritos(view.getContext());
                            for (int i1 = 0; i1 < personas.size(); i1++) {
                                if (personas.get(i1).getPais().contains("Estados Unidos")) {
                                    //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                                    listaPersonas.add(personas.get(i1));
                                }
                            }
                        }else {
                            if (i==3){
                                //BDd.cargarFavoritos(view.getContext());
                                //mService.cargarFavoritos(view.getContext());
                                for (int i1 = 0; i1 < personas.size(); i1++) {
                                    if (personas.get(i1).getPais().contains("Italia")) {
                                        //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                                        listaPersonas.add(personas.get(i1));
                                    }
                                }
                            }else{
                                //BDd.cargarFavoritos(view.getContext());
                                //mService.cargarFavoritos(view.getContext());
                                for (int i1 = 0; i1 < personas.size(); i1++) {
                                    if (personas.get(i1).getPais().contains("Cuba")) {
                                        //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                                        listaPersonas.add(personas.get(i1));
                                    }
                                }
                            }

                        }
                    }
                }
                //BDd.cargarFavoritos(view.getContext());
                mService.cargarFavoritos(view.getContext());
                guardar_en_lista2(listaPersonas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        personas=new ArrayList<>();
        //BDd.cargarFavoritos(vista.getContext());
        //personas = BDd.getPersonas();

        return vista;
    }


    public void cargar(){
        personas=mService.getPersonas();
        //Toast.makeText(getActivity(), ""+mService.getPersonas().get(0).getNombre(), Toast.LENGTH_SHORT).show();
        //mService.getPersona();
    }

    public void guardar_en_lista2(final ArrayList<Persona> listaDpersonas) {

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
                    ArrayList<Persona> listaFiltrada= adaptadorListaNombres.getFiltrer(listaDpersonas,newText);
                    selectLista(listaFiltrada);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    //INICIAR EL RECYCLE Y HACER ALGO DE ACUERDO A LO QUE SELECCIONE DE LA LISTA
    private void selectLista(final ArrayList<Persona> listaDpersonas){

        //BDd.cargarFavoritos(getContext());
        mService.cargarFavoritos(getContext());
        recyclePersonas.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorListaNombres= new AdaptadorListaNombres(listaDpersonas);
        adaptadorListaNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaDpersonas.get(
                        recyclePersonas.getChildAdapterPosition(view)).isFavorito()){

                    //SI ES FAVORITO PARA QUE LA LISTA SEPA QUE CAMBIO A FALSO
                    listaDpersonas.get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(false);

                    //PARA QUE CAMBIE EN LA BASE DE DATOS
                    /*for (int i=0;i<mService.getPersonas().size();i++){
                        if (listaDpersonas.get(
                                recyclePersonas.getChildAdapterPosition(view)).getNombre()==mService.getPersonas().get(i).getNombre()){

                            mService.getPersonas().get(
                                    recyclePersonas.getChildAdapterPosition(view)).setFavorito(false);
                        }
                    }*/


                    mService.guardarFavoritos(getContext());

                    adaptadorListaNombres.notifyDataSetChanged();

                }else{

                    listaDpersonas.get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);

                    /*for (int i=0;i<mService.getPersonas().size();i++){
                        if (listaDpersonas.get(
                                recyclePersonas.getChildAdapterPosition(view)).getNombre()==mService.getPersonas().get(i).getNombre()){

                            mService.getPersonas().get(
                                    recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);
                        }
                    }*/



                    mService.guardarFavoritos(getContext());
                    adaptadorListaNombres.notifyDataSetChanged();
                }

            }
        });

        recyclePersonas.setAdapter(adaptadorListaNombres);
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
