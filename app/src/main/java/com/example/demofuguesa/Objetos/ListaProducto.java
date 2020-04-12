package com.example.demofuguesa.Objetos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Adaptadores.AdaptadorProducto;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Detalleventa;
import com.example.demofuguesa.Entidades.Producto;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaProducto extends AppCompatActivity {
    private RecyclerView rvJugador;
    private AdaptadorProducto adapter;
    private TextView vacio;
    Detalleventa detalleVenta=Detalleventa.getInstance();
    private Button vercarrito;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadeproductos);
        rvJugador = (RecyclerView) findViewById(R.id.r_listaproductos);
        vercarrito=(Button)findViewById(R.id.b_vercarrito);
        vacio=(TextView)findViewById(R.id.tv_vacio);
        adapter = new AdaptadorProducto(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvJugador.setLayoutManager(l);
        rvJugador.setAdapter(adapter);
        foto=(ImageView)findViewById(R.id.i_producto);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.PRODUCTO_REFERENCE+"/"+recibir_producto());
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                vacio.setText("");
                Producto producto=dataSnapshot.getValue(Producto.class);
                adapter.addProducto(producto);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Producto producto=dataSnapshot.getValue(Producto.class);
                adapter.addProducto(producto);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Producto producto=dataSnapshot.getValue(Producto.class);
                adapter.addProducto(producto);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Producto producto=dataSnapshot.getValue(Producto.class);
                adapter.addProducto(producto);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Cliente c=Cliente.getInstance();
        if(c.getUsuario()==null)
        {
            vercarrito.setVisibility(View.INVISIBLE);
        }
        else
        {
            vercarrito.setVisibility(View.VISIBLE);
        }
        vercarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivityCarrito();
            }
        });

       if(adapter.tamaño())
       {
           vacio.setText("vacío");
       }


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
        verifyStoragePermissions(this);
    }
    private void setScrollbar(){
        rvJugador.scrollToPosition(adapter.getItemCount()-1);
    }
    public static boolean verifyStoragePermissions(ListaProducto activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_EXTERNAL_STORAGE = 1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }else{
            return true;
        }
    }

    private void nextActivityCarrito(){
        startActivity(new Intent(ListaProducto.this, ListaCompras.class));

    }

    public String recibir_producto()
    {
        Bundle extras=getIntent().getExtras();
        String referencia=extras.getString("referencia");

        return referencia;
    }

}
