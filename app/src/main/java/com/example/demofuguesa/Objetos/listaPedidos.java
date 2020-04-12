package com.example.demofuguesa.Objetos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.demofuguesa.Adaptadores.AdaptadorListaPedidos;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class listaPedidos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorListaPedidos adapter;
    private TextView vacio;
    private Cliente cliente=Cliente.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        vacio=(TextView)findViewById(R.id.tv_vacio);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referenciaFactura=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.FACTURA_REFERENCE+"/"+cliente.getUsuario());
        LinearLayoutManager l = new LinearLayoutManager(this);
        adapter= new AdaptadorListaPedidos(this);
        recyclerView=(RecyclerView)findViewById(R.id.r_listapedidos);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);


        referenciaFactura.orderByChild("dia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                vacio.setText("");
                Factura factura= dataSnapshot.getValue(Factura.class);
                if(factura.isTipo()==true) {
                    adapter.agregar(factura, dataSnapshot.getKey());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                vacio.setText("");

                adapter.borrar();
                referenciaFactura.orderByChild("dia").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Factura factura= dataSnapshot.getValue(Factura.class);
                        if(factura.isTipo()==true) {
                            adapter.agregar(factura, dataSnapshot.getKey());
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
}
