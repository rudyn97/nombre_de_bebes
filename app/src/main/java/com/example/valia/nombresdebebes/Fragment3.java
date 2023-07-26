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

public class Fragment3 extends Fragment/* implements AdapterView.OnItemSelectedListener*/{
    Spinner spinner;
    //String[] count = {"...",""+getResources().getString(R.string.masculino), ""+getResources().getString(R.string.femenino)};
    //AQUI SALE DE ACUERDO AL IDIOMA
    //String[] count = {"...","Masculino", "Femenino"};

    ArrayList<String> origen;

    ArrayList<PersonaSign> personas;
    android.widget.SearchView buscador;
    RecyclerView recyclePersonas;
    AdaptadorListaNombresSign adaptadorListaNombres;

    DATOS mService1;

    //boolean ejecutado = false;
    static boolean start = true;

    private static final String ARG_SECTION_NUMBER = "section_number";


    public static Fragment3 newInstance(int sectionNumber) {

        Fragment3 fragment = new Fragment3();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public Fragment3() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment1,container,false);

        Intent in = new Intent(vista.getContext(), DATOS.class);
        vista.getContext().bindService(in, mConnection1, Context.BIND_AUTO_CREATE);

        //Toast.makeText(vista.getContext(), "Create", Toast.LENGTH_SHORT).show();

        //ejecutado=false;

        origen= new ArrayList<>();
        origen.add("...");
        origen.add(""+getString(R.string.africano));
        origen.add(""+getString(R.string.aleman));
        origen.add(""+getString(R.string.americano));
        origen.add(""+getString(R.string.anglosajon));
        origen.add(""+getString(R.string.arabe));
        origen.add(""+getString(R.string.azteca));
        origen.add(""+getString(R.string.caldeo));
        origen.add(""+getString(R.string.canario));
        origen.add(""+getString(R.string.catalan));
        origen.add(""+getString(R.string.celta));
        origen.add(""+getString(R.string.checo));
        origen.add(""+getString(R.string.chino));
        origen.add(""+getString(R.string.danes));
        origen.add(""+getString(R.string.egipcio));
        origen.add(""+getString(R.string.escoces));
        origen.add(""+getString(R.string.eslavo));
        origen.add(""+getString(R.string.fenicio));
        origen.add(""+getString(R.string.frances));
        origen.add(""+getString(R.string.gales));
        origen.add(""+getString(R.string.gallego));
        origen.add(""+getString(R.string.germano));
        origen.add(""+getString(R.string.griego));
        origen.add(""+getString(R.string.hawaiano));
        origen.add(""+getString(R.string.hebreo));
        origen.add(""+getString(R.string.hindu));
        origen.add(""+getString(R.string.ingles));
        origen.add(""+getString(R.string.irlandes));
        origen.add(""+getString(R.string.italiano));
        origen.add(""+getString(R.string.latino));
        origen.add(""+getString(R.string.nahuatl));
        origen.add(""+getString(R.string.noruego));
        origen.add(""+getString(R.string.persa));
        origen.add(""+getString(R.string.provenzal));
        origen.add(""+getString(R.string.quechua));
        origen.add(""+getString(R.string.ruso));
        origen.add(""+getString(R.string.sevillano));
        origen.add(""+getString(R.string.sueco));
        origen.add(""+getString(R.string.vasco));

        personas=new ArrayList<>();
        //personas = BDd.getPersonas();

        /*Intent datos = new Intent(getActivity(), DATOS.class);
        getActivity().startService(datos);*/


        //PARA EL SPINNER
        spinner = (Spinner) vista.findViewById(R.id.spinner1);
        //spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,origen);
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
                    Toast.makeText(mService1.getApplicationContext(), "select", Toast.LENGTH_SHORT).show();
                }*/

                cargar();
                //Toast.makeText(mService1.getApplicationContext(), "select", Toast.LENGTH_SHORT).show();

                //PARA FILTRAR DATOS DE ACUERDO A LA SELECCION DEL SPINNER
                //Persona p;
                ArrayList<PersonaSign> listaPersonas= new ArrayList<>();

                switch (i){
                    case 1:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Africano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 2:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Áleman")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 3:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Americano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 4:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Anglosajón")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 5:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Árabe")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 6:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Azteca")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 7:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Caldeo")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 8:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Canario")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 9:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Catalán")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 10:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Celta")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 11:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Checo")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 12:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Chino")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 13:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Danés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 14:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Egipcio")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 15:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Escocés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 16:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Eslavo")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 17:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Fenicio")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 18:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Francés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 19:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Galés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 20:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Gallego")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 21:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Germano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 22:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Griego")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 23:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Hawaiano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 24:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Hebreo")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 25:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Hindú")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 26:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Inglés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 27:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Irlandés")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 28:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Italiano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 29:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Latino")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 30:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Náhuatl")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 31:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Noruego")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 32:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Persa")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 33:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Provenzal")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 34:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Quechua")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 35:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Ruso")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 36:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Sevillano")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 37:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Sueco")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    case 38:{
                        for (int i1=0;i1<personas.size();i1++){
                            if (personas.get(i1).getOrigen().contains("Vasco")){
                                listaPersonas.add(personas.get(i1));
                            }
                        }
                        break;
                    }
                    default:{
                        listaPersonas.clear();
                        listaPersonas=personas;
                        break;
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
        personas=mService1.getPersonasSign();
        //Toast.makeText(getActivity(), ""+mService.getPersonas().get(0).getNombre(), Toast.LENGTH_SHORT).show();
        //mService.getPersona();
    }

    //PARA GUARDAR EN LA LISTA Y PARA QUE BUSQUE EN ESA LISTA
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
        //ESTO ES PA QUE LO COJA SOLO LA 1ra VEZ

        //BDd.cargarFavoritos(getContext());
        mService1.cargarFavoritosSign(getContext());

        recyclePersonas.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorListaNombres= new AdaptadorListaNombresSign(listaDpersonas);
        adaptadorListaNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaDpersonas.get(
                        recyclePersonas.getChildAdapterPosition(view)).isFavorito()){

                    //SI ES FAVORITO PARA QUE LA LISTA SEPA QUE CAMBIO A FALSO
                    listaDpersonas.get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(false);

                    //PARA QUE CAMBIE EN LA BASE DE DATOS
                    /*for (int i=0;i<mService1.getPersonasSign().size();i++){
                        if (listaDpersonas.get(
                                recyclePersonas.getChildAdapterPosition(view)).getNombre()==mService1.getPersonasSign().get(i).getNombre()){

                            mService1.getPersonasSign().get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(false);
                        }
                    }*/

                    mService1.guardarFavoritosSign(getContext());
                    adaptadorListaNombres.notifyDataSetChanged();

                }else{

                    listaDpersonas.get(
                            recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);

                    /*for (int i=0;i<mService1.getPersonasSign().size();i++){
                        if (listaDpersonas.get(
                                recyclePersonas.getChildAdapterPosition(view)).getNombre()==mService1.getPersonasSign().get(i).getNombre()){

                            mService1.getPersonasSign().get(
                                    recyclePersonas.getChildAdapterPosition(view)).setFavorito(true);
                        }
                    }*/


                    mService1.guardarFavoritosSign(getContext());
                    adaptadorListaNombres.notifyDataSetChanged();
                }

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
