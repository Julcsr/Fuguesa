package com.example.demofuguesa.Adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Holder.HolderFactura;
import com.example.demofuguesa.Login.RegistrarActivity;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListaPedidosCliente;
import com.example.demofuguesa.Objetos.ListaProductoVista;
import com.example.demofuguesa.Objetos.ListarProductoSeleccionado;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdaptadorListaPedidosClientes extends RecyclerView.Adapter<HolderFactura>{
    Dialog dialog;
    String dni;
    TextView mensajeEstado;
    TextView montoPedido;
    String llavesita;
    Button botonEstado;

    int pos;
    private Context c;
    Calendar date=Calendar.getInstance();
    private List<Factura> facturaList= new ArrayList<>();
    private List<String>keylist=new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference referenciaFactura=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.FACTURA_REFERENCE);
    final DatabaseReference referenciaCliente=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.CLIENTE_REFERENCE);


    public AdaptadorListaPedidosClientes(Context c) {
        this.c = c;
    }
    public void agregar(Factura factura, String key)
    {

        if(factura.isEstado()==false)
        {
                    if(String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("2") || String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("1") ||String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("7")   )
                    {
                        if(factura.getDia().getDay()==5||factura.getDia().getDay()==6||factura.getDia().getDay()==0)
                        { facturaList.add(factura);
                        keylist.add(key);
                        notifyItemInserted(facturaList.size());}
                    }
                   else if(String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("4")||String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("3"))
                    {
                        if(factura.getDia().getDay()==1||factura.getDia().getDay()==2)
                        {
                        facturaList.add(factura);
                        keylist.add(key);
                        notifyItemInserted(facturaList.size());}
                    }
                    else if(String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("6")||String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("5"))
                    {
                        if(factura.getDia().getDay()==3||factura.getDia().getDay()==4)
                        {
                        facturaList.add(factura);
                        keylist.add(key);
                        notifyItemInserted(facturaList.size());}
                    }
        }

    }

    public void BorrarTodo() {
        facturaList.clear();
        keylist.clear();
        notifyDataSetChanged();
    }
    @Override
    public HolderFactura onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.activity_item_pedido,parent,false);


        dialog= new Dialog(c);
        dialog.setContentView(R.layout.activity_floatingdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText contraseña=(EditText)dialog.findViewById(R.id.e_contraseña);
        Button cancelar=(Button)dialog.findViewById(R.id.b_cancelar);
        Button aceptar=(Button)dialog.findViewById(R.id.b_aceptar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(  contraseña.getText().toString().equals(dni))
                {


                    AlertDialog.Builder builder= new AlertDialog.Builder(c);
                    builder.setCancelable(false);
                    builder.setTitle("La entrega se realizó con exito");
                    builder.setMessage("Se registró la entrega exitosa ");
                    builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                    referenciaFactura.child(dni).child(keylist.get(pos)).child("estado").setValue(true);
                    mensajeEstado.setText("Entregado");
                    mensajeEstado.setTextColor(Color.BLUE);
                    botonEstado.setEnabled(false);
                    contraseña.setText("");

                    referenciaCliente.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Cliente cliente=dataSnapshot.getValue(Cliente.class);

                            if(cliente.getUsuario().equals(dni)) {
                                llavesita = dataSnapshot.getKey();
                                referenciaCliente.child(llavesita).child("saldoRestante").setValue(cliente.getSaldoRestante() + Double.parseDouble(montoPedido.getText().toString().trim()));
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



                    dialog.hide();


                }
                else
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(c);
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

        return new HolderFactura(v);

    }

    @Override
    public void onBindViewHolder(final HolderFactura holder, final int position) {



        final Factura factura=facturaList.get(position);

        holder.getFechaemision().setText(factura.getFecha());
        holder.getMonto().setText(String.valueOf(factura.getPago()));

        if(facturaList.get(position).isEstado()==true)
        {
            holder.getEstado().setText("Entregado");
            holder.getEstado().setTextColor(Color.BLUE);
            holder.getVermas().setText("Entrega exitosa");
            holder.getVermas().setTextColor(Color.BLUE);

        }

        else if(facturaList.get(position).isEstado()==false)
        {
            holder.getEstado().setText("Pendiente");
            holder.getEstado().setTextColor(Color.RED);
        }


        if(factura.isConfirmacion()==false && facturaList.get(position).isEstado()==false)

        {
            holder.getVermas().setText("  Requiere Confirmación  ");
        }

        else if(factura.isConfirmacion()==true && facturaList.get(position).isEstado()==false)
        {
            holder.getVermas().setText("  Listo para confirmar  ");
        }


        if(factura.isConfirmacion()==true && facturaList.get(position).isEstado()==false)
        {
            holder.getVermas().setEnabled(true);
        }

        else if(factura.isConfirmacion()==false && facturaList.get(position).isEstado()==false || factura.isConfirmacion()==true &&facturaList.get(position).isEstado()==true )
        {
            holder.getVermas().setEnabled(false);
        }

        holder.getVermas().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montoPedido=holder.getMonto();
                mensajeEstado=holder.getEstado();
                botonEstado=holder.getVermas();
                pos=position;
                dni=facturaList.get(position).getDni();
                dialog.show();


            }
        });

        holder.getVerpedido().setEnabled(true);
        holder.getVerpedido().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c, ListaProductoVista.class);
                i.putExtra("dni",facturaList.get(position).getDni() );
                i.putExtra("key",keylist.get(position) );
                c.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return facturaList.size();
    }
    public boolean tamaño() {
        return facturaList.isEmpty();
    }


}
