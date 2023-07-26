package com.example.valia.nombresdebebes;

/**
 * Created by Valia on 27/12/2019.
 */

public class PersonaSign {
    String nombre;
    String origen;
    String significado;
    String sexo;
    boolean favorito;

    public PersonaSign(String nombre, String origen, String significado, String sexo, boolean favorito) {
        this.nombre = nombre;
        this.origen = origen;
        this.significado = significado;
        this.sexo = sexo;
        this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public int getIdImagenSexo(){

        if (sexo.contains("masculino")){
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

    public int getIdImagenSexoColor(){

        if (sexo.contains("masculino")){
                return R.drawable.hombre1;
        }else{
                return R.drawable.mujer1;
        }
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }
}
