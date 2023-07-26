package com.example.valia.nombresdebebes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by rudyng on 11/04/2022.
 */

public class BottomSheetDialog extends BottomSheetDialogFragment {

    Button btnApklis;
    Button btnUptodown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        btnApklis = (Button) v.findViewById(R.id.btnApklis);
        btnUptodown = (Button) v.findViewById(R.id.btnUptodown);

        btnApklis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apklis(view);
            }
        });
        btnUptodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uptodown(view);
            }
        });

        return v;
    }

    public void apklis(View view){

        //Toast.makeText(view.getContext(), "Abrir en apklis", Toast.LENGTH_SHORT).show();

        //ACTIVAR AL SUBIR LA DE CUENTOS EN APKLIS

        String appName = "cu.uci.android.apklis";
        boolean isAppIntalled = available(appName);

        if (isAppIntalled) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Intent.ACTION_SEND));
            i.setData(Uri.parse("https://www.apklis.cu/application/com.goldennumber.rudyn.nombresdebebescuentos"));
            i.setPackage(appName);
            startActivity(i);
        }else {
            //No tiene apklis mandarlo pal navegador
            String url = "https://www.apklis.cu/application/com.goldennumber.rudyn.nombresdebebescuentos";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }

    public void uptodown(View view){
        //Toast.makeText(view.getContext(), "Abrir uptodown", Toast.LENGTH_SHORT).show();

        //ACTIVAR AL SUBIR LA DE CUENTOS EN UPTODOWN

        //POR NAVEGADOR
        String url = "https://nombres-de-bebes-cuentos.uptodown.com/android";
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
