package com.example.demofuguesa.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class  Factura  implements Serializable {
    private static Factura factura;
    private String fecha;
    private double pago;
    private boolean estado;
    private boolean confirmacion;
    private Date dia;
    private List<String>listaCompras=new ArrayList<>();
    private String dni;
    private boolean tipo; //1=pedido 0=pago

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public boolean isTipo() {
        return tipo;
    }
    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public static Factura getInstance()
    {
        if(factura==null)
        {
            factura=new Factura();
        }
        return factura;
    }

    public boolean isConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }


    public void eliminar(int i)
    {
        listaCompras.remove(i);
    }

    public void agregarListaCompras(String p)
    {
        listaCompras.add(p);
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Factura(){}

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public static Factura getFactura() {
        return factura;
    }

    public static void setFactura(Factura factura) {
        Factura.factura = factura;
    }
    public List<String> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<String> listaCompras) {
        this.listaCompras = listaCompras;
    }
}
