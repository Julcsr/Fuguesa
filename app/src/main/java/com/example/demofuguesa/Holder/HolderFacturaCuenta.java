package com.example.demofuguesa.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.R;


public class HolderFacturaCuenta extends RecyclerView.ViewHolder {
    private TextView fechaemision;
    private TextView monto;
    private TextView tipo;

    public TextView getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(TextView fechaemision) {
        this.fechaemision = fechaemision;
    }

    public TextView getMonto() {
        return monto;
    }

    public void setMonto(TextView monto) {
        this.monto = monto;
    }

    public TextView getTipo() {
        return tipo;
    }

    public void setTipo(TextView tipo) {
        this.tipo = tipo;
    }

    public HolderFacturaCuenta(View itemView) {
        super(itemView);
        fechaemision = (TextView) itemView.findViewById(R.id.tv_fechaPago);
        monto=(TextView)itemView.findViewById(R.id.tv_montoPago);
        tipo=(TextView)itemView.findViewById(R.id.tv_tipoPago);
           }

}
