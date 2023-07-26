package com.example.valia.nombresdebebes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Valia on 27/08/2021.
 */

public class SliderFragment extends Fragment {
    View view;
    TextView titulo, contenido;
    ImageView image;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slider,container,false);
        image=(ImageView) view.findViewById(R.id.idImageViewSlider);
        titulo=(TextView) view.findViewById(R.id.idTextViewNombre);
        contenido=(TextView) view.findViewById(R.id.idTextViewContenido);
        RelativeLayout idRelativeFondoSlider=(RelativeLayout)view.findViewById(R.id.idRelativeFondoSlider);
        if (getArguments()!=null){
            titulo.setText(getArguments().getString("titulo"));
            contenido.setText(getArguments().getString("contenido"));
            image.setImageResource(getArguments().getInt("imagen"));
        }

        return view;
    }
}
