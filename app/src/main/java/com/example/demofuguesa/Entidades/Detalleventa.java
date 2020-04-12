package com.example.demofuguesa.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Detalleventa {

    static Detalleventa detalleventa;
    private Producto producto;
    private String cliente;
    private String cantidad;
    private String fecha;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Detalleventa(){
    }

    public static Detalleventa getInstance()
    {
        if(detalleventa==null)
        {
            detalleventa=new Detalleventa();
        }
        return detalleventa;
    }


    public static Detalleventa getDetalleventa() {
        return detalleventa;
    }

    public static void setDetalleventa(Detalleventa detalleventa) {
        Detalleventa.detalleventa = detalleventa;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}