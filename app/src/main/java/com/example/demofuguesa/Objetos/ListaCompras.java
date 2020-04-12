package com.example.demofuguesa.Objetos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Detalleventa;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaCompras extends AppCompatActivity {
    double totalDeuda=0;
    double monto = 0;
    double dmontoTotal = 0;
    double cantidadProductos=0;
    List<Detalleventa>detalleVentaList=new ArrayList<>();
    List<Detalleventa>listaDeuda=new ArrayList<>();
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat simpleDateFormat;
    Cliente cliente=Cliente.getInstance();
    Button eliminartodo,agregarCarrito;
    Factura factura=Factura.getInstance();
    int id=0;
   List<String> arrayID=new ArrayList<>();
    TableLayout tabla ;
    int idrow=0;
    TableRow tr;
    TextView textView;
    TextView  montoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detalleventa);
        super.onCreate(savedInstanceState);
        eliminartodo=(Button)findViewById(R.id.b_borrartodo);
        agregarCarrito=(Button)findViewById(R.id.b_generarcompra);
        tabla = (TableLayout) findViewById(R.id.table);
        montoTotal=(TextView)findViewById(R.id.t_imhere);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referenciaFactura=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.FACTURA_REFERENCE);
        final DatabaseReference referenciaVenta=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.VENTA_REFERENCE +"/"+cliente.getUsuario());



            referenciaVenta.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Detalleventa detalleventa=dataSnapshot.getValue(Detalleventa.class);

                    try {
                        for (String f:factura.getListaCompras()) {
                            if(f.equals(dataSnapshot.getKey()))
                            {
                                detalleVentaList.add(detalleventa);
                                agregar_carrito(detalleventa,id);
                                arrayID.add(dataSnapshot.getKey());
                                id=id+1;
                            }
                        }
                    }

                    catch (Exception e)
                    {
                        return;
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


        referenciaFactura.child(cliente.getUsuario()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Factura factura=dataSnapshot.getValue(Factura.class);
                if(factura.isTipo()==true && factura.isEstado()==false)
                {calcular_Deuda(factura.getPago());}
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



        eliminartodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isTimeAutomaticEnabled(getApplicationContext())) {

                    if (cantidadProductos <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
                        builder.setCancelable(false);
                        builder.setTitle("Error al agregar su pedido");
                        builder.setMessage("¡Parece que ud no cuenta con productos en el carrito!");
                        builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        eliminartodo.setEnabled(false);
                        agregarCarrito.setEnabled(false);
                    } else {
                        montoTotal.setText("S/." + 0);
                        for (String venta : arrayID) {
                            referenciaVenta.child(venta).removeValue();
                        }
                    }


                    tabla.removeAllViews();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
                    builder.setCancelable(false);
                    builder.setTitle("Error al eliminar su pedido");
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

        agregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c=Calendar.getInstance();


                    if (isTimeAutomaticEnabled(getApplicationContext())) {

                        if (cantidadProductos <= 4) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
                            builder.setCancelable(false);
                            builder.setTitle("Error al agregar su pedido");
                            builder.setMessage("La cantidad mínima de productos es de 5 unidades");
                            builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            if (dmontoTotal > 0) {


                                if (((totalDeuda + cliente.getSaldoRestante() + dmontoTotal) <= cliente.getSaldoAsignado())) {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(ListaCompras.this);
                                    builder.setCancelable(true);
                                    builder.setTitle("Confirmación de pedido");
                                    builder.setMessage("Solo los pedidos realizados podrán cancelarse antes de las 12:00 PM. ¿Desea continuar?");
                                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            simpleDateFormat = new SimpleDateFormat("dd-MMM-yyy hh:mm a");
                                            String dateTime = simpleDateFormat.format(calendar.getTime());
                                            factura.setFecha(dateTime);
                                            factura.setPago(dmontoTotal);
                                            factura.setEstado(false);
                                            factura.setTipo(true);
                                            factura.setDni(cliente.getUsuario());
                                            factura.setDia(calendar.getTime());
                                            factura.setConfirmacion(false);
                                            referenciaFactura.child(cliente.getUsuario()).push().setValue(factura);
                                            tabla.removeAllViews();
                                            montoTotal.setText("S/." + 0);
                                            detalleVentaList.clear();
                                            factura.getListaCompras().clear();
                                            nextActivityEntregapedido();
                                            dmontoTotal = 0;
                                        }
                                    });
                                    builder.show();


                                }
                                else {
                                    double e = totalDeuda + cliente.getSaldoRestante() + dmontoTotal;

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
                                    builder.setCancelable(false);
                                    builder.setTitle("Error al agregar su pedido");
                                    builder.setMessage("No puede superar el crédito disponibles para los clientes ");
                                    builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();

                                }
                            } else if (dmontoTotal <= 0) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
                                builder.setCancelable(false);
                                builder.setTitle("Error al agregar su pedido");
                                builder.setMessage("¡Parece que ud no cuenta con productos en el carrito!");
                                builder.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                                eliminartodo.setEnabled(false);
                                agregarCarrito.setEnabled(false);
                            }
                        }
                    }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaCompras.this);
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

    };

    public  void calcular_Deuda(double d)
    {
        totalDeuda=totalDeuda+d;


    }
    public void agregar_carrito(Detalleventa ventas,int identidad) {

        String eliminar="Eliminar";
        monto = Double.valueOf(ventas.getCantidad()) * ventas.getProducto().getPreciounit();
        dmontoTotal=dmontoTotal+monto;
        cantidadProductos=Double.valueOf(ventas.getCantidad())+cantidadProductos;
        String[] cadena = {ventas.getProducto().getNombre() + "-"+ ventas.getProducto().getCaracteristica(), ventas.getCantidad(), ventas.getProducto().getPreciounit().toString(),String.valueOf(monto), eliminar};


        final TableRow row = new TableRow(this);
        for (int i = 0; i < 5; i++) {

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(15, 15, 15, 15);
            textView.setText(cadena[i]);
            textView.setId(identidad);
            if(i==4)
            {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int clicked_id = v.getId();
                    dmontoTotal=dmontoTotal-Double.valueOf(detalleVentaList.get(clicked_id).getCantidad()) * detalleVentaList.get(clicked_id).getProducto().getPreciounit();
                    if(dmontoTotal<0)
                    {
                        dmontoTotal=0;
                    }
                    cantidadProductos=cantidadProductos-Double.valueOf(detalleVentaList.get(clicked_id).getCantidad());
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.VENTA_REFERENCE +"/"+cliente.getUsuario()+"/"+arrayID.get(clicked_id));
                    ref.removeValue();
                    tabla.removeView(row);
                    montoTotal.setText("S/."+String.valueOf(dmontoTotal));



                    }
                });
            }
            textView.setTextColor(Color.BLACK);
            row.addView(textView,newTablerowParams());

            if(i==4)
            {
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.color.blanco);
            }

        }
        row.setBackgroundColor(Color.WHITE);
        row.setId(identidad);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tabla.setColumnStretchable(3,true);
        tabla.addView(row);
        montoTotal.setText("S/."+String.valueOf(dmontoTotal));
    }

    public static boolean isTimeAutomaticEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    private TableRow.LayoutParams newTablerowParams()
    {
        TableRow.LayoutParams params=new TableRow.LayoutParams();
        params.setMargins(2,2,2,2);
        params.weight=1;
        return params;
    }



    private void nextActivityEntregapedido(){
        startActivity(new Intent(ListaCompras.this, listaPedidos.class));

    }

}