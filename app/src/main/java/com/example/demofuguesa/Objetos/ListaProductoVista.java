package com.example.demofuguesa.Objetos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Detalleventa;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Inicio.ListaInicial;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaProductoVista extends AppCompatActivity {
    Cliente cliente = Cliente.getInstance();
    Button aceptarPedido, cancelarpedido;
    List<String> arrayID = new ArrayList<>();
    List<String> arrayIDAuxiliar = new ArrayList<>();
    TableLayout tabla;
    double dmontoTotal=0;
    TextView textView;
    TextView montoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detalleventa);
        super.onCreate(savedInstanceState);

        aceptarPedido = (Button) findViewById(R.id.b_borrartodo);
        cancelarpedido = (Button) findViewById(R.id.b_generarcompra);
        aceptarPedido.setText("Aceptar pedido");
        cancelarpedido.setText("Cancelar pedido");
        tabla = (TableLayout) findViewById(R.id.table);
        montoTotal = (TextView) findViewById(R.id.t_imhere);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referenciaFactura = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.FACTURA_REFERENCE + "/" + recibir_usuario());
        final DatabaseReference referenciaVenta = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.VENTA_REFERENCE + "/" + recibir_usuario());

        referenciaFactura.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(recibir_key().equals(dataSnapshot.getKey())) {
                    final Factura factura = dataSnapshot.getValue(Factura.class);
                    arrayID=factura.getListaCompras();

                    referenciaVenta.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Detalleventa detalleventa=dataSnapshot.getValue(Detalleventa.class);

                             if(arrayID.contains(dataSnapshot.getKey()))
                            {
                                agregar_carrito(detalleventa);
                                arrayIDAuxiliar.add(dataSnapshot.getKey());

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

        aceptarPedido.setVisibility(View.INVISIBLE);



        cancelarpedido.setVisibility(View.INVISIBLE);

    }

    public void agregar_carrito(Detalleventa ventas) {
        double monto=0;
        monto = Double.valueOf(ventas.getCantidad()) * ventas.getProducto().getPreciounit();
        dmontoTotal=monto+dmontoTotal;
        String[] cadena = {ventas.getProducto().getNombre(), ventas.getCantidad(), ventas.getProducto().getPreciounit().toString(), String.valueOf(monto)};


        final TableRow row = new TableRow(this);
        for (int i = 0; i < 4; i++) {

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.blanco);
            textView.setText(cadena[i]);
            textView.setTextColor(Color.BLACK);
            row.addView(textView);
        }
        tabla.addView(row);
        montoTotal.setText("S/." + String.valueOf(dmontoTotal));
    }


    public String recibir_key()
    {
        Bundle extras=getIntent().getExtras();
        String key=extras.getString("key");

        return key;
    }

    public String recibir_usuario()
    {
        Bundle extras=getIntent().getExtras();
        String usuario=extras.getString("dni");

        return usuario;
    }


}