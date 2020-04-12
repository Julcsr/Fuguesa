package com.example.demofuguesa.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListaCuentaCliente;
import com.example.demofuguesa.Objetos.ListaProducto;
import com.example.demofuguesa.Objetos.listaPedidos;
import com.example.demofuguesa.R;

public class ListaInicial extends AppCompatActivity {

    GridLayout gl;
    private Button salir,verPedidos,miEstadoCuenta;

    private ImageView griferia,accesoriosrepuestos,importaciones,serviciosindustriales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inicio);
        final Cliente cliente = Cliente.getInstance();
        gl=(GridLayout)findViewById(R.id.gridl);
        salir = (Button) findViewById(R.id.b_salir);
        miEstadoCuenta=(Button)findViewById(R.id.b_miEstadoCuenta);
        griferia = (ImageView) findViewById(R.id.i_griferia);
        accesoriosrepuestos = (ImageView) findViewById(R.id.i_accesoriosrepuestos);
        importaciones = (ImageView) findViewById(R.id.i_importaciones);
        serviciosindustriales = (ImageView) findViewById(R.id.i_serviciosindustriales);
        accesoriosrepuestos = (ImageView) findViewById(R.id.i_accesoriosrepuestos);
        verPedidos=(Button)findViewById(R.id.b_pedidos);

        miEstadoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityListaPagos();
            }
        });

        verPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityGoListaPedidos();
            }
        });

        accesoriosrepuestos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivitySalir();
                cliente.setUsuario(null);
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
        Intent i=new Intent(ListaInicial.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.SERVICIOS_REFERENCE);
        startActivity(i);
    }

    private void nextActivityImportaciones(){
        Intent i=new Intent(ListaInicial.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.IMPORTACIONES_REFERENCE);
        startActivity(i);
    }

    private void nextActivityAccesorios(){
        Intent i=new Intent(ListaInicial.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.ACCESORIOS_REFERENCE);
        startActivity(i);
    }

    private void nextActivityGriferia(){
        Intent i=new Intent(ListaInicial.this, ListaProducto.class);
        i.putExtra("referencia", FirebaseReferences.GRIFERIA_REFERENCE);
        startActivity(i);
    }

  private void nextActivitySalir(){
        startActivity(new Intent(ListaInicial.this, ListaInicialSinLogin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }


    private void nextActivityGoListaPedidos()
    {

        startActivity(new Intent(ListaInicial.this, listaPedidos.class));
    }

    private void nextActivityListaPagos()
    {

        startActivity(new Intent(ListaInicial.this, ListaCuentaCliente.class));
    }
}

