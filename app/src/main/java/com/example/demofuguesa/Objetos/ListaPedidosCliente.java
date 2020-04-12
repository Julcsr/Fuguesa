package com.example.demofuguesa.Objetos;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Adaptadores.AdaptadorListaPedidos;
import com.example.demofuguesa.Adaptadores.AdaptadorListaPedidosClientes;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidosCliente extends AppCompatActivity {

    private TextView vacio;
    private RecyclerView recyclerView;
    private AdaptadorListaPedidosClientes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referenciaFactura=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.FACTURA_REFERENCE+"/"+recibir_llave());
        LinearLayoutManager l = new LinearLayoutManager(this);
        vacio=(TextView)findViewById(R.id.tv_vacio);
        adapter= new AdaptadorListaPedidosClientes(this);
        recyclerView=(RecyclerView)findViewById(R.id.r_listapedidos);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);



        referenciaFactura.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Factura factura= dataSnapshot.getValue(Factura.class);
                if(factura.isTipo()==true) {

                    adapter.agregar(factura, dataSnapshot.getKey());
                    if(!adapter.tamaño()){
                        vacio.setText("");

                    }
                }



            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                adapter.BorrarTodo();
                referenciaFactura.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Factura factura= dataSnapshot.getValue(Factura.class);
                        if(factura.isTipo()==true) {
                            adapter.agregar(factura, dataSnapshot.getKey());
                            if(!adapter.tamaño()){
                                vacio.setText("");

                            }
                        }

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

                if(adapter.tamaño()){
                    vacio.setText("No hay pedidos");

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

        if(adapter.tamaño())
        {
            vacio.setText("No hay pedidos");
        }




    }


    public String recibir_llave()
    {
        Bundle extras=getIntent().getExtras();
        String referencia=extras.getString("key");

        return referencia;
    }
}
