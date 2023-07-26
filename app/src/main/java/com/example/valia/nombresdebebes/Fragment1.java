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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 24/12/2019.
 */

public class Fragment1 extends Fragment/* implements AdapterView.OnItemSelectedListener*/{
    Spinner spinner;
    //String[] count = {"...",""+getResources().getString(R.string.masculino), ""+getResources().getString(R.string.femenino)};
    //AQUI SALE DE ACUERDO AL IDIOMA
    //String[] count = {"...","Masculino", "Femenino"};

    ArrayList<String> sexo;

    ArrayList<Persona> personas;
    android.widget.SearchView buscador;
    RecyclerView recyclePersonas;
    AdaptadorListaNombres adaptadorListaNombres;

    DATOS mService;

    //boolean ejecutado = false;

    private static final String ARG_SECTION_NUMBER = "section_number";


    public static Fragment1 newInstance(int sectionNumber) {

        Fragment1 fragment = new Fragment1();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public Fragment1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment1,container,false);

        Intent in = new Intent(vista.getContext(), DATOS.class);
        vista.getContext().bindService(in, mConnection, Context.BIND_AUTO_CREATE);


        sexo= new ArrayList<>();
        sexo.add("...");
        sexo.add(""+getString(R.string.masculino));
        sexo.add(""+getString(R.string.femenino));

        personas=new ArrayList<>();
        //personas = BDd.getPersonas();

        /*Intent datos = new Intent(getActivity(), DATOS.class);
        getActivity().startService(datos);*/


        //PARA EL SPINNER
        spinner = (Spinner) vista.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,sexo);
        spinner.setAdapter(adapter);

        //PARA LA BUSQUEDA Y LA LISTA
        buscador = (android.widget.SearchView) vista.findViewById(R.id.buscar1);
        recyclePersonas = (RecyclerView) vista.findViewById(R.id.recycle);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*if (!ejecutado){
                    cargar();
                    ejecutado=true;
                }*/

                cargar();

                //PARA FILTRAR DATOS DE ACUERDO A LA SELECCION DEL SPINNER
                //Persona p;
                ArrayList<Persona> listaPersonas= new ArrayList<>();
                if (i==1){
                    for (int i1=0;i1<personas.size();i1++){
                        if (personas.get(i1).getSexo().contains("Masc")){

                            //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                            listaPersonas.add(personas.get(i1));
                        }
                    }
                }else{
                    if (i==2){
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getSexo().contains("Feme")){

                                //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                    }else{
                /*for (int i1=0;i1<personas.size();i1++){
                    //Toast.makeText(getContext(), ""+personas.get(1), Toast.LENGTH_SHORT).show();
                    //p=new Persona(personas.get(i1).getNombre(),personas.get(i1).getSexo(),personas.get(i1).getPais(),personas.get(i1).isFavorito());
                    listaPersonas.add(personas.get(i1));
                }*/
                        listaPersonas.clear();
                        listaPersonas=personas;

                    }
                }
                guardar_en_lista1(listaPersonas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return vista;
    }

    public void cargar(){
        personas=mService.getPersonas();
        //Toast.makeText(getActivity(), ""+mService.getPersonas().get(0).getNombre(), Toast.LENGTH_SHORT).show();
        //mService.getPersona();
    }


    //PARA GUARDAR EN LA LISTA Y PARA QUE BUSQUE EN ESA LISTA
    public void guardar_en_lista1(final ArrayList<Persona> listaDpersonas) {

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
        //ESTO ES PA QUE LO COJA SOLO LA 1ra VEZ

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

                    //PARA SI NO ES FAVORITO
                }else{

                    listaDpersonas.get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);

                    /*for (int i=0;i<mService.getPersonas().size();i++){
                        if (listaDpersonas.get(
                                recyclePersonas.getChildAdapterPosition(view)).getNombre()==mService.getPersonas().get(i).getNombre()){

                            mService.getPersonas().get(recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);

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
