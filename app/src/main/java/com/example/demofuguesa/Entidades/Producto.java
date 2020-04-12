package com.example.demofuguesa.Entidades;

import java.io.Serializable;

public class Producto implements Serializable {
    private static Producto producto;
    private String nombre;
    private Double preciounit;
    private String imagen;
    private Integer cantidadminima;

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    private String caracteristica;

    public int getCantidadminima() {
        return cantidadminima;
    }

    public void setCantidadminima(Integer cantidadminima) {
        this.cantidadminima = cantidadminima;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private String tipo;

    private Producto(){}

    public static Producto getInstance()
    {
        if(producto==null)
        {
            producto=new Producto();
        }
        return producto;
    }

    public  Double getPreciounit() {
        return preciounit;
    }

    public void setPreciounit( Double preciounit) {
        this.preciounit = preciounit;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
