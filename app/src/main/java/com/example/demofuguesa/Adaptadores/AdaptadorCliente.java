package com.example.demofuguesa.Adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Empleado;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Holder.HolderCliente;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListaPedidosCliente;
import com.example.demofuguesa.Objetos.ListarProductoSeleccionado;
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


public class AdaptadorCliente extends RecyclerView.Adapter<HolderCliente> {

    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat simpleDateFormat;
    Empleado empleado=Empleado.getInstance();
    Factura factura=Factura.getInstance();
    int pos;
    Dialog dialog;
    private List<Cliente>clienteList=new ArrayList<>();
    private Context c;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference referenciaFactura=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.FACTURA_REFERENCE);
    final DatabaseReference referenciaCliente=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.CLIENTE_REFERENCE);

    public AdaptadorCliente(Context c) {
        this.c = c;
    }

    public void BorrarTodo() {
       clienteList.clear();
        notifyDataSetChanged();
    }

    public void addCliente(Cliente cl)
    {
     clienteList.add(cl);
     notifyItemInserted(clienteList.size());
    }

    @Override
    public HolderCliente onCreateViewHolder(ViewGroup parent, int viewType) {

        dialog= new Dialog(c);
        dialog.setContentView(R.layout.activity_floating_dialog_factura);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText contraseña=(EditText)dialog.findViewById(R.id.e_contraseña);
        final EditText montoPago=(EditText)dialog.findViewById(R.id.e_montoPago);
        final Button cancelar=(Button)dialog.findViewById(R.id.b_cancelarFactura);
        Button aceptar=(Button)dialog.findViewById(R.id.b_aceptarFactura);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(montoPago.getText().toString().isEmpty())
                {
                    montoPago.setText("0");
                }
                if(  contraseña.getText().toString().equals(clienteList.get(pos).getUsuario()))
                {



                    factura.setTipo(false);
                    factura.setDni(empleado.getUsuario());
                    factura.setPago(Double.valueOf(montoPago.getText().toString()));
                    simpleDateFormat=new SimpleDateFormat("dd-MMM-yyy hh:mm a");
                    String dateTime=simpleDateFormat.format(calendar.getTime());
                    factura.setFecha(dateTime);
                    factura.setDia(calendar.getTime());
                    factura.setConfirmacion(true);
                    factura.setEstado(true);
                    factura.setListaCompras(null);



                    if(clienteList.get(pos).getSaldoRestante()>=Double.valueOf(montoPago.getText().toString()))
                    {

                        AlertDialog.Builder builder= new AlertDialog.Builder(c);
                        builder.setCancelable(false);
                        builder.setTitle("El pago se realizó con exito");
                        builder.setMessage("Se registró el pago ");
                        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        referenciaCliente.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                Cliente cliente=dataSnapshot.getValue(Cliente.class);

                                if(cliente.getUsuario().equals(clienteList.get(pos).getUsuario())) {

                                    referenciaCliente.child(dataSnapshot.getKey()).child("saldoRestante").setValue(cliente.getSaldoRestante() - Double.valueOf(montoPago.getText().toString()));
                                    referenciaFactura.child(clienteList.get(pos).getUsuario()).push().setValue(factura);

                                    contraseña.setText("");
                                    montoPago.setText("");
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
                    else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(c);
                        builder.setCancelable(false);
                        builder.setTitle("Error al realizar el pago");
                        builder.setMessage("Ingrese un monto válido");
                        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        contraseña.setText("");
                        montoPago.setText("");
                    }

                    dialog.hide();
                }


            else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(false);
                    builder.setTitle("Contraseña incorrecta");
                    builder.setMessage("Ingrese nuevamente la contraseña ");
                    builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    contraseña.setText("");
                }
            }
        });
        View v = LayoutInflater.from(c).inflate(R.layout.activity_itemcliente,parent,false);
        return new HolderCliente(v);
    }


    @Override
    public void onBindViewHolder(HolderCliente holder, final int position) {

        holder.getNombreCliente().setText(clienteList.get(position).getNombre());
        holder.getApellidoPaternoCliente().setText(clienteList.get(position).getApellidopaterno());
        holder.getApellidoMaterno().setText(clienteList.get(position).getApellidomaterno());
        holder.getSaldoAsignado().setText(String.valueOf(clienteList.get(position).getSaldoAsignado()));
        holder.getSaldoRestante().setText(String.valueOf(clienteList.get(position).getSaldoRestante()));
        holder.getVerPedidos().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c, ListaPedidosCliente.class);
                i.putExtra("key",clienteList.get(position).getUsuario() );
                c.startActivity(i);
            }
        });

        holder.getPagarFactura().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos=position;
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    public void filtrar(ArrayList<Cliente>filtroCliente)
    {
        this.clienteList=filtroCliente;
        notifyDataSetChanged();
    }

}
