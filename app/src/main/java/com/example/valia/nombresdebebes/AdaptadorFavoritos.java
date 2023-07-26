package com.example.valia.nombresdebebes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Valia on 3/1/2020.
 */

public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.NombresViewHolder> implements View.OnClickListener {

    ArrayList<Persona> listaPersonas;
    private View.OnClickListener listener;

    public AdaptadorFavoritos(ArrayList<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @Override
    public AdaptadorFavoritos.NombresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_de_favoritos,parent,false);
        view.setOnClickListener(this);

        return new AdaptadorFavoritos.NombresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorFavoritos.NombresViewHolder holder, int position) {
        holder.nombres.setText(listaPersonas.get(position).getNombre());
        holder.sexo.setImageResource(listaPersonas.get(position).getIdImagenSexo());
        holder.pais.setImageResource(listaPersonas.get(position).getIdImagenPais());
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class NombresViewHolder extends RecyclerView.ViewHolder {

        TextView nombres;
        ImageView sexo;
        ImageView pais;

        public NombresViewHolder(View itemView) {
            super(itemView);
            nombres = (TextView) itemView.findViewById(R.id.idFavNombre);
            sexo = (ImageView) itemView.findViewById(R.id.idFavSexo);
            pais = (ImageView) itemView.findViewById(R.id.idFavPais);
        }
    }

    public ArrayList<Persona> getFiltrer(ArrayList<Persona> persona,String texto){
        ArrayList<Persona> listaFiltrada=new ArrayList<Persona>();

        try {
            texto=texto.toLowerCase();

            for (Persona person: persona ){
                String person2=person.getNombre().toLowerCase();

                if (person2.contains(texto)){
                    listaFiltrada.add(person);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaFiltrada;
    }
}