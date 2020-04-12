package com.example.demofuguesa.Objetos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateUtils;
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
import com.example.demofuguesa.Login.RegistrarActivity;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListarProductoSeleccionado extends AppCompatActivity {
    Cliente cliente = Cliente.getInstance();

    Button aceptarPedido, cancelarpedido;
    List<String> arrayID = new ArrayList<>();
    List<String> arrayIDAuxiliar = new ArrayList<>();
    TableLayout tabla;
    double dmontoTotal=0;
    TextView textView;
    Factura fac= Factura.getInstance();
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
        final DatabaseReference referenciaFactura = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.FACTURA_REFERENCE + "/" + cliente.getUsuario());
        final DatabaseReference referenciaVenta = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.VENTA_REFERENCE + "/" + cliente.getUsuario());

        referenciaFactura.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(recibir_key().equals(dataSnapshot.getKey())) {

                    final Factura factura = dataSnapshot.getValue(Factura.class);
                   fac.setDia(factura.getDia());
                    arrayID=factura.getListaCompras();
                    if(factura.isConfirmacion()==false) {
                        aceptarPedido.setEnabled(true);
                    }
                    else if(factura.isConfirmacion()==true)
                    {
                        aceptarPedido.setText("Pedido confirmado");
                        aceptarPedido.setEnabled(false);
                        cancelarpedido.setEnabled(false);
                    }
                    referenciaVenta.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Detalleventa detalleventa=dataSnapshot.getValue(Detalleventa.class);

                           if(arrayID.contains(dataSnapshot.getKey()))
                                {
                                        agregar_carrito(detalleventa);
                                        arrayIDAuxiliar.add(dataSnapshot.getKey());

                                    }
//                                }

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

            aceptarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isTimeAutomaticEnabled(getApplicationContext())) {
                        boolean confirmacion = true;
                        referenciaFactura.child(recibir_key()).child("confirmacion").setValue(true);
                        referenciaFactura.child(recibir_key()).child("listaCompras").setValue(arrayIDAuxiliar);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ListarProductoSeleccionado.this);
                        builder.setCancelable(false);
                        builder.setTitle("Se verificó correctamente la entrega");
                        builder.setMessage("¡Entrega disponible para confirmar! ");
                        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                aceptarPedido.setEnabled(false);
                                aceptarPedido.setText("Pedido confirmado");
                                cancelarpedido.setEnabled(false);
                            }
                        });
                        builder.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListarProductoSeleccionado.this);
                        builder.setCancelable(false);
                        builder.setTitle("Error al aceptar su pedido");
                        builder.setMessage("Debe activar la hora y fecha automática de su dispositivo en ajustes/hora y fecha/hora y fecha automática");
                        builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                }
            });




    cancelarpedido.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


              if(isTimeAutomaticEnabled(getApplicationContext())){
                Calendar c=Calendar.getInstance();
                Date d=c.getTime();
           if(d.getHours()<12 && (d.before(fac.getDia()) || DateUtils.isToday(fac.getDia().getTime()))) {



                AlertDialog.Builder builder = new AlertDialog.Builder(ListarProductoSeleccionado.this);
                builder.setCancelable(true);
                builder.setTitle("¿Está seguro que desea cancelar su pedido?");
                builder.setMessage("Este proceso no se podrá revertir");
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        referenciaFactura.child(recibir_key()).removeValue();
                        Toast.makeText(ListarProductoSeleccionado.this, "Pedido cancelado", Toast.LENGTH_SHORT).show();
                        nextActivityInicio();
                    }
                });
                builder.show();
            }
            else
                 {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarProductoSeleccionado.this);
                builder.setCancelable(false);
                builder.setTitle("Error al cancelar su pedido");
                builder.setMessage("Los pedidos solo pueden cancelarse antes de las 12:00 pm");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                 }


            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarProductoSeleccionado.this);
                builder.setCancelable(false);
                builder.setTitle("Error al aceptar su pedido");
                builder.setMessage("Debe activar la hora y fecha automática de su dispositivo para continuar");
                builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }
    });

    }

    public static boolean isTimeAutomaticEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }
    public void agregar_carrito(Detalleventa ventas) {
          double monto=0;
          monto = Double.valueOf(ventas.getCantidad()) * ventas.getProducto().getPreciounit();
          dmontoTotal=monto+dmontoTotal;
          String[] cadena = {ventas.getProducto().getNombre()+ventas.getProducto().getCaracteristica()







                  , ventas.getCantidad(), ventas.getProducto().getPreciounit().toString(), String.valueOf(monto)};


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

    private void nextActivityInicio() {
        startActivity(new Intent(ListarProductoSeleccionado.this, ListaInicial.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}


