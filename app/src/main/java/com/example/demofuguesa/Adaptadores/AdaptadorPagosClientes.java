package com.example.demofuguesa.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.Entidades.Factura;
import com.example.demofuguesa.Holder.HolderFacturaCuenta;
import com.example.demofuguesa.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPagosClientes extends RecyclerView.Adapter<HolderFacturaCuenta> {

    private Context c;
    private List<Factura> facturaList= new ArrayList<>();
    private List<String>keylist=new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public AdaptadorPagosClientes(Context c) {
        this.c = c;
    }
    public void agregar(Factura factura, String key)
    {
        if(factura.isConfirmacion()==true && factura.isEstado()==true)
        {  facturaList.add(factura);
        keylist.add(key);
        notifyItemInserted(facturaList.size());}
    }

    public void BorrarTodo() {
        facturaList.clear();
        keylist.clear();
        notifyDataSetChanged();
    }

    @Override
    public HolderFacturaCuenta onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.activity_item_cuenta_cliente,parent,false);
        return new HolderFacturaCuenta(v);
    }
    @Override
    public void onBindViewHolder(final HolderFacturaCuenta holder, final int position) {

        holder.getFechaemision().setText( facturaList.get(position).getFecha());


        if(facturaList.get(position).isTipo()==true)
        {

            holder.getTipo().setText("Pedido ");
            holder.getTipo().setTextColor(Color.RED);
            holder.getMonto().setText("S/ "+"+"+facturaList.get(position).getPago());
    }

        else if(facturaList.get(position).isTipo()==false)
        {

            holder.getTipo().setText("Pago ");
            holder.getTipo().setTextColor(Color.GREEN);
            holder.getMonto().setText("S/ "+"-"+facturaList.get(position).getPago());
        }
    }

    @Override
    public int getItemCount() {
        return facturaList.size();
    }

    public boolean tamaño()
    {
        return  facturaList.isEmpty();
    }

}
