package com.example.demofuguesa.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Login.LoginActivity;
import com.example.demofuguesa.Login.RegistrarActivity;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListaProducto;
import com.example.demofuguesa.R;

public class ListaInicialSinLogin extends AppCompatActivity {

    private Button iniciarsesion,registrarse,salir,verLista,miestadoCuenta;
    private ImageView griferia,accesoriosrepuestos,importaciones,serviciosindustriales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Cliente cliente=Cliente.getInstance();
        iniciarsesion = (Button) findViewById(R.id.b_iniciarsesion);
        registrarse = (Button) findViewById(R.id.b_registrarse);
        griferia = (ImageView) findViewById(R.id.i_griferia);
        accesoriosrepuestos = (ImageView) findViewById(R.id.i_accesoriosrepuestos);
        importaciones = (ImageView) findViewById(R.id.i_importaciones);
        serviciosindustriales = (ImageView) findViewById(R.id.i_serviciosindustriales);
        accesoriosrepuestos = (ImageView) findViewById(R.id.i_accesoriosrepuestos);
        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityLogin();
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityRegistrar();
            }
        });
        serviciosindustriales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityServicios();
            }
        });

        importaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityImportaciones();
            }
        });

        griferia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityGriferia();
            }
        });

        accesoriosrepuestos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityAccesorios();
            }
        });

    }

    private void nextActivityServicios(){
        Intent i=new Intent(ListaInicialSinLogin.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.SERVICIOS_REFERENCE);
        startActivity(i);
    }

    private void nextActivityImportaciones(){
        Intent i=new Intent(ListaInicialSinLogin.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.IMPORTACIONES_REFERENCE);
        startActivity(i);
    }

    private void nextActivityAccesorios(){
        Intent i=new Intent(ListaInicialSinLogin.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.ACCESORIOS_REFERENCE);
        startActivity(i);
    }

    private void nextActivityGriferia(){
        Intent i=new Intent(ListaInicialSinLogin.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.GRIFERIA_REFERENCE);
        startActivity(i);
    }

    private void nextActivityLogin(){

        startActivity(new Intent(ListaInicialSinLogin.this, LoginActivity.class));
    }
    private void nextActivityRegistrar(){
        startActivity(new Intent(ListaInicialSinLogin.this, RegistrarActivity.class));

    }
}