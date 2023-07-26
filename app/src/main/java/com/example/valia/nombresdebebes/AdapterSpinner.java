package com.example.valia.nombresdebebes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Valia on 26/12/2019.
 */


public class AdapterSpinner extends ArrayAdapter {

    int[]imageArray;
    ArrayList<String> titleArray;

    public AdapterSpinner(@NonNull Context context, ArrayList<String> titles1, int [] img1) {
        super(context,R.layout.example_cusspinner_view,R.id.idTitle,titles1);
        this.imageArray=img1;
        this.titleArray=titles1;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.example_cusspinner_view,parent,false);

        ImageView myImage= (ImageView) row.findViewById(R.id.idPic);
        TextView myTitle= (TextView) row.findViewById(R.id.idTitle);

        myImage.setImageResource(imageArray[position]);
        myTitle.setText(titleArray.get(position));

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView,parent);
    }

    private  View initView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.example_cusspinner_view,parent,false);

        ImageView myImage= (ImageView) row.findViewById(R.id.idPic);
        TextView myTitle= (TextView) row.findViewById(R.id.idTitle);

        myImage.setImageResource(imageArray[position]);
        myTitle.setText(titleArray.get(position));

        return row;
    }

}
