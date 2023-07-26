package com.example.valia.nombresdebebes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Valia on 27/08/2021.
 */

public class SliderFragmentMas extends Fragment {
    View view;
    ImageView image;
    LinearLayout idApklis, idUptodown;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slider_mas,container,false);
        image=(ImageView) view.findViewById(R.id.idImageViewSlider);
        idApklis=(LinearLayout) view.findViewById(R.id.idApklis);
        idUptodown=(LinearLayout) view.findViewById(R.id.idUptodown);

        //RelativeLayout idRelativeFondoSlider=(RelativeLayout)view.findViewById(R.id.idRelativeFondoSlider);
        if (getArguments()!=null){

            image.setImageResource(getArguments().getInt("imagen"));

            //SI NO TIENE ARGUMENTO NO SE MUESTRA NI NADA
            if (getArguments().getString("apklis").equals("")){
                idApklis.setVisibility(View.INVISIBLE);
            }else {
                idApklis.setVisibility(View.VISIBLE);
                idApklis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Direccion Apklis
                        //Toast.makeText(getContext(), ""+getArguments().getString("apklis"), Toast.LENGTH_SHORT).show();
                        apklis(view,getArguments().getString("apklis"));
                    }
                });
            }

            //SI NO TIENE ARGUMENTO NO SE MUESTRA NI NADA
            if (getArguments().getString("uptodown").equals("")){
                idUptodown.setVisibility(View.INVISIBLE);
            }else {
                idUptodown.setVisibility(View.VISIBLE);
                idUptodown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Direccion Uptodown
                        //Toast.makeText(getContext(), ""+getArguments().getString("uptodown"), Toast.LENGTH_SHORT).show();
                        uptodown(view,getArguments().getString("uptodown"));

                    }
                });
            }

        }

        return view;
    }


    private void apklis(View view,String direccion){

        //Toast.makeText(view.getContext(), "Abrir en apklis", Toast.LENGTH_SHORT).show();

        String appName = "cu.uci.android.apklis";
        boolean isAppIntalled = available(appName);

        if (isAppIntalled) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Intent.ACTION_SEND));
            i.setData(Uri.parse(direccion));
            i.setPackage(appName);
            startActivity(i);
        }else {
            //No tiene apklis mandarlo pal navegador
            String url = direccion;
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }

    private void uptodown(View view, String direccion){

        //Toast.makeText(view.getContext(), "Abrir uptodown", Toast.LENGTH_SHORT).show();

        //POR NAVEGADOR
        String url = direccion;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }

    //////////METODO SABER SI ESTA//////////////////
    private boolean available(String nombre){
        boolean available = true;
        try {
            getActivity().getPackageManager().getPackageInfo(nombre,0);
        }catch (PackageManager.NameNotFoundException e){
            available = false;
        }
        return available;
    }


}
