package com.example.demofuguesa.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Holder.HolderFactura;
import com.example.demofuguesa.Objetos.ListarProductoSeleccionado;
import com.example.demofuguesa.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaPedidos extends RecyclerView.Adapter<HolderFactura>{


    private Context c;
    private List<Factura> facturaList= new ArrayList<>();
    private List<String>keylist=new ArrayList<>();


    public AdaptadorListaPedidos(Context c) {
        this.c = c;
    }
    public void agregar(Factura factura, String key)
    {
        facturaList.add(factura);
        keylist.add(key);
        notifyItemInserted(facturaList.size());
    }
    public void borrar()
    {
        facturaList.clear();
        keylist.clear();
        notifyDataSetChanged();
    }


    @Override
    public HolderFactura onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.activity_item_pedido,parent,false);
        return new HolderFactura(v);
    }

    @Override
    public void onBindViewHolder(final HolderFactura holder, final int position) {
        final Factura factura = facturaList.get(position);
        holder.getFechaemision().setText(factura.getFecha());
        holder.getMonto().setText("S/ "+String.valueOf(factura.getPago()));
        holder.getVerpedido().setVisibility(View.INVISIBLE);
        if(factura.isEstado()==false)
        {
            holder.getEstado().setText("Pendiente");
            holder.getEstado().setTextColor(Color.RED);

        }
        else
        {
            holder.getEstado().setText("Entregado");
            holder.getEstado().setTextColor(Color.BLUE);
        }

        holder.getVermas().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(c, ListarProductoSeleccionado.class);
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
