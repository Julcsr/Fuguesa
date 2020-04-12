package com.example.demofuguesa.Entidades;

import java.io.Serializable;

public class Cliente implements Serializable {

    private static Cliente cliente;
    private String nombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private String numerotelefonico;
    private String usuario;//DNI
    private String contraseña; //posible restricción


    private Cliente(){}

    public static Cliente getInstance()
    {
        if(cliente==null)
        {
            cliente=new Cliente();
        }
        return cliente;
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public double getSaldoAsignado() {
        return saldoAsignado;
    }

    public void setSaldoAsignado(double saldoAsignado) {
        this.saldoAsignado = saldoAsignado;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    private double saldoAsignado;
    private double saldoRestante;



    public static void setCliente(Cliente cliente) {
        Cliente.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getNumerotelefonico() {
        return numerotelefonico;
    }

    public void setNumerotelefonico(String numerotelefonico) {
        this.numerotelefonico = numerotelefonico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
