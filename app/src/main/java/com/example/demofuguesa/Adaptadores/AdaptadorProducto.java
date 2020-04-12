package com.example.demofuguesa.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Detalleventa;
import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Entidades.Producto;
import com.example.demofuguesa.Holder.HolderProducto;
import com.example.demofuguesa.Objetos.Activity_VisualizarProducto;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListarProductoSeleccionado;
import com.example.demofuguesa.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdaptadorProducto extends RecyclerView.Adapter<HolderProducto> {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference(FirebaseReferences.IMGPRODUCTO_REFERENCE);
    Factura factura=Factura.getInstance();
    String key;
    private List<Integer> contador = new ArrayList<>();
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat simpleDateFormat;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
     Detalleventa detalleVenta=Detalleventa.getInstance();
     Cliente cliente=Cliente.getInstance();
    DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.VENTA_REFERENCE+"/"+cliente.getUsuario());

    private List<Producto> productoList = new ArrayList<>();
    private Context c;

    public AdaptadorProducto(Context c) {
        this.c = c;

    }

    public void addProducto(Producto p)
    {
        productoList.add(p);
        contador.add(0);

        notifyItemInserted(productoList.size());
    }


    public int aumentar(int position)
    {
        contador.set(position,contador.get(position)+1);

        return contador.get(position);
    }

    public int disminuir(int position)
    {
        if(contador.get(position)>0) {
            contador.set(position, contador.get(position) - 1);
        }
        return contador.get(position);
    }

    @Override
    public HolderProducto onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.activity_itemproducto,parent,false);
        return new HolderProducto(v);
    }

    @Override
    public void onBindViewHolder(final HolderProducto holder, final int position) {
        holder.getNombreProducto().setText(productoList.get(position).getNombre());

        Glide.with(c).load(productoList.get(position).getImagen()).into(holder.getFotoPerfil());

        holder.getAumentar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.getMostrar().setText(String.valueOf(aumentar(position)));
            }
        });
        holder.getReducir().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getMostrar().setText(String.valueOf(disminuir(position)));
            }
        });

        holder.getFotoPerfil().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(c, Activity_VisualizarProducto.class);
                i.putExtra("imagen",productoList.get(position).getImagen() );
                i.putExtra("caracteristica",productoList.get(position).getCaracteristica() );
                i.putExtra("precio",String.valueOf(productoList.get(position).getPreciounit() ));
                i.putExtra("cantidadminima",String.valueOf(productoList.get(position).getCantidadminima()));
                i.putExtra("nombre",productoList.get(position).getNombre());
                c.startActivity(i);

            }
        });

        if(cliente.getUsuario()==null) {
            holder.getReducir().setVisibility(View.INVISIBLE);
            holder.getAumentar().setVisibility(View.INVISIBLE);
            holder.getMostrar().setVisibility(View.INVISIBLE);
            holder.getCarrito().setVisibility(View.INVISIBLE);
        }

        holder.getCarrito().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(  Integer.parseInt(holder.getMostrar().getText().toString())>=productoList.get(position).getCantidadminima()) {

                    try {
                        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyy hh:mm:ss a");
                        String dateTime = simpleDateFormat.format(calendar.getTime());
                        detalleVenta.setProducto(productoList.get(position));
                        detalleVenta.setDate(calendar.getTime());
                        detalleVenta.setCliente(cliente.getUsuario());
                        detalleVenta.setCantidad(String.valueOf(contador.get(position)));
                        detalleVenta.setFecha(dateTime);
                        Toast.makeText(c, "Se añadieron: " + contador.get(position) + " productos" + " al carrito", Toast.LENGTH_SHORT).show();
                        key= reference.push().getKey();
                        if(factura.getListaCompras()==null)
                        {
                            List<String> s=new ArrayList<>();
                            factura.setListaCompras(s);

                        }
                        factura.agregarListaCompras(key);
                        reference.child(key).setValue(detalleVenta);
                        contador.set(position,0);
                        holder.getMostrar().setText(String.valueOf(0));
                        notifyDataSetChanged();
                    }
                    catch (Exception e)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setCancelable(false);
                        builder.setTitle("Error");
                        builder.setMessage("¡Ups, algo pasó! Te recomendamos cerrar la aplicación y reintentarlo nuevamente");
                        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        return ;
                    }

                }
                else
                {
                    Toast.makeText(c,"La cantidad mínima por producto es de: "+productoList.get(position).getCantidadminima()+ " unidades",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }
    public boolean tamaño() {
        return productoList.isEmpty();
    }

}
