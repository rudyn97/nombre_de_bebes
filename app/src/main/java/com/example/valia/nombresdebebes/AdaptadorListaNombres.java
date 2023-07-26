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

public class AdaptadorListaNombres extends RecyclerView.Adapter<AdaptadorListaNombres.NombresViewHolder> implements View.OnClickListener {

    ArrayList<Persona> listaPersonas;
    private View.OnClickListener listener;

    public AdaptadorListaNombres(ArrayList<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @Override
    public NombresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_de_lista,parent,false);
        view.setOnClickListener(this);

        return new NombresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NombresViewHolder holder, int position) {
        holder.nombres.setText(listaPersonas.get(position).getNombre());
        holder.sexo.setImageResource(listaPersonas.get(position).getIdImagenSexo());
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

        public NombresViewHolder(View itemView) {
            super(itemView);
            nombres = (TextView) itemView.findViewById(R.id.textView2);
            sexo = (ImageView) itemView.findViewById(R.id.imageView3);
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
