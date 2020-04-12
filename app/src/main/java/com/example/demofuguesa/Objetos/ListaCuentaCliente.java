package com.example.demofuguesa.Objetos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demofuguesa.Adaptadores.AdaptadorListaPedidosClientes;
import com.example.demofuguesa.Adaptadores.AdaptadorPagosClientes;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Login.RegistrarActivity;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaCuentaCliente extends AppCompatActivity {
    TextView nombreUusario, saldoAsignado, saldoRestante, mensajeImportante;
    private Cliente cliente = Cliente.getInstance();
    private TextView vacio;
    boolean estado = false;
    private RecyclerView recyclerView;
    private AdaptadorPagosClientes adapter;
    private List<Factura>facturaList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cuenta_cliente);
        nombreUusario = (TextView) findViewById(R.id.tv_nombreUsuario);
        saldoAsignado = (TextView) findViewById(R.id.tv_SaldoAsignado);
        mensajeImportante=(TextView)findViewById(R.id.tv_mensajeImportante);
        saldoRestante = (TextView) findViewById(R.id.tv_saldorestante);
        vacio=(TextView)findViewById(R.id.tv_vacio);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referenciaFactura = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.FACTURA_REFERENCE + "/" + cliente.getUsuario());
        final DatabaseReference referenciaUsuario = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.CLIENTE_REFERENCE );
        LinearLayoutManager l = new LinearLayoutManager(this);
        adapter= new AdaptadorPagosClientes(this);
        recyclerView=(RecyclerView)findViewById(R.id.r_listaCuentaCliente);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);

        nombreUusario.setText(cliente.getNombre().toString()+" "+cliente.getApellidopaterno().toString()+" "+ cliente.getApellidomaterno().toString());
        saldoAsignado.setText(String.valueOf(cliente.getSaldoAsignado()));
        saldoRestante.setText(String.valueOf(cliente.getSaldoRestante()));

        if(cliente.getSaldoRestante()<cliente.getSaldoAsignado())
        {
            mensajeImportante.setText("Usted cuenta con crédito suficiente para realizar compras");
            mensajeImportante.setTextColor(Color.BLUE);
        }

        if(cliente.getSaldoRestante()>=cliente.getSaldoAsignado())
        {
            mensajeImportante.setText("Usted ha excedido el crédito otorgado a los clientes");
            mensajeImportante.setTextColor(Color.RED);
        }

        referenciaUsuario.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Cliente clientefb=dataSnapshot.getValue(Cliente.class);
                if(clientefb.getUsuario().equals(cliente.getUsuario())) {
                    cliente.setSaldoAsignado(clientefb.getSaldoAsignado());
                    cliente.setSaldoRestante(clientefb.getSaldoRestante());
                    if (cliente.getSaldoRestante() < cliente.getSaldoAsignado()) {

                        mensajeImportante.setText("Usted cuenta con crédito suficiente para realizar compras");
                        mensajeImportante.setTextColor(Color.BLUE);
                    }

                    if (cliente.getSaldoRestante() >= cliente.getSaldoAsignado()) {
                        mensajeImportante.setText("Usted ha excedido el crédito otorgado a los clientes");
                        mensajeImportante.setTextColor(Color.RED);
                    }
                    saldoAsignado.setText(String.valueOf(cliente.getSaldoAsignado()));
                    saldoRestante.setText(String.valueOf(cliente.getSaldoRestante()));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        referenciaFactura.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    vacio.setText("");

                Factura factura= dataSnapshot.getValue(Factura.class);
                adapter.agregar(factura,dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                adapter.BorrarTodo();
                referenciaFactura.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        vacio.setText("");
                        Factura factura= dataSnapshot.getValue(Factura.class);
                        adapter.agregar(factura,dataSnapshot.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        if(adapter.tamaño())
        {
            vacio.setText("vacío");
        }

    }




}
