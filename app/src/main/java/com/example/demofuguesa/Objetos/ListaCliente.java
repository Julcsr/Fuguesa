package com.example.demofuguesa.Objetos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Adaptadores.AdaptadorCliente;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaCliente extends AppCompatActivity {
    private RecyclerView rvCliente;
    private AdaptadorCliente adapter;
    EditText buscador;
    List<Cliente>listaClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_lista_clientes);
        buscador=(EditText)findViewById(R.id.et_buscador);


        final  FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.CLIENTE_REFERENCE);



        rvCliente = (RecyclerView) findViewById(R.id.r_listaclientes);

        rvCliente.setLayoutManager(new GridLayoutManager(this,1));

        listaClientes=new ArrayList<>();



        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cliente cliente=dataSnapshot.getValue(Cliente.class);
                adapter.addCliente(cliente);
                listaClientes.add(cliente);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cliente cliente=dataSnapshot.getValue(Cliente.class);
                  adapter.BorrarTodo();
                  listaClientes.clear();
                  reference.addChildEventListener(new ChildEventListener() {
                      @Override
                      public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          Cliente cliente=dataSnapshot.getValue(Cliente.class);
                          adapter.addCliente(cliente);
                          listaClientes.add(cliente);
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
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        adapter = new AdaptadorCliente(this);

        rvCliente.setAdapter(adapter);

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

    }


    public void filtrar(String texto)
    {
        ArrayList<Cliente>filtrarLista= new ArrayList<>();

        for (Cliente cliente:listaClientes) {
            if(cliente.getNombre().toLowerCase().contains(texto.toLowerCase()))
            {
                filtrarLista.add(cliente);
            }

        }
        adapter.filtrar(filtrarLista);
    }

}
