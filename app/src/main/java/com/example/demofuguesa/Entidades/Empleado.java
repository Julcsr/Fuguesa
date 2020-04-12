package com.example.demofuguesa.Entidades;

import java.io.Serializable;

public class Empleado implements Serializable {

        private static Empleado empleado;
        private String usuario;
        private String contraseña;
        private String nombre;
        private String apellidopaterno;
        private String apellidomatern;
        private String numerotelefonico;

        private Empleado(){}

        public static Empleado getInstance()
        {
            if(empleado==null)
            {
                empleado=new Empleado();
            }
            return empleado;
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

    public static Empleado getEmpleado() {
        return empleado;
    }

    public static void setEmpleado(Empleado empleado) {
        Empleado.empleado = empleado;
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

    public String getApellidomatern() {
        return apellidomatern;
    }

    public void setApellidomatern(String apellidomatern) {
        this.apellidomatern = apellidomatern;
    }

    public String getNumerotelefonico() {
        return numerotelefonico;
    }

    public void setNumerotelefonico(String numerotelefonico) {
        this.numerotelefonico = numerotelefonico;
    }
}
