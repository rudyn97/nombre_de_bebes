package com.example.valia.nombresdebebes;

/**
 * Created by Valia on 27/12/2019.
 */

public class Persona {
    String nombre;
    String sexo;
    String pais;
    boolean favorito;

    public Persona(String nombre, String sexo, String pais, boolean favorito) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.pais = pais;
        this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public String getPais() {
        return pais;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public int getIdImagenSexo(){

        if (sexo.contains("Masculino")){
            if (favorito){
                return R.drawable.hombre1;
            }else{
                return R.drawable.hombre;
            }
        }else{
            if (favorito){
                return R.drawable.mujer1;
            }else{
                return R.drawable.mujer;
            }
        }
    }

    public int getIdImagenPais(){
        int id=R.drawable.i1;

        if (pais.contains("Uruguay")) id=R.drawable.i1;
        else if (pais.contains("España")) id=R.drawable.i2;
                else if (pais.contains("Estados Unidos")) id=R.drawable.i3;
                        else if (pais.contains("Italia")) id=R.drawable.i4;
                                else if (pais.contains("Cuba")) id=R.drawable.i5;
                                    else if (pais.contains("Vacio")) id=R.drawable.i6_transparente;


/*
        switch (pais){
            case "Uruguay":{
                id=R.drawable.i1;
                break;
            }
            case "España":{
                id=R.drawable.i2;
                break;
            }
            case "Estados Unidos":{
                id=R.drawable.i3;
                break;
            }
            case "Italia":{
                id=R.drawable.i4;
                break;
            }
        }*/
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
