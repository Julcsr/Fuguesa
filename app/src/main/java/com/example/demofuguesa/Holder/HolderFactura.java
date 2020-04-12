package com.example.demofuguesa.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.R;


public class HolderFactura extends RecyclerView.ViewHolder {
    private TextView fechaemision;
    private TextView estado;
    private TextView monto;
    private Button verpedido;
    private Button vermas;

    public TextView getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(TextView fechaemision) {
        this.fechaemision = fechaemision;
    }

    public TextView getEstado() {
        return estado;
    }

    public void setEstado(TextView estado) {
        this.estado = estado;
    }

    public TextView getMonto() {
        return monto;
    }

    public void setMonto(TextView monto) {
        this.monto = monto;
    }

    public Button getVerpedido() {
        return verpedido;
    }

    public void setVerpedido(Button verpedido) {
        this.verpedido = verpedido;
    }

    public Button getVermas() {
        return vermas;
    }

    public void setVermas(Button vermas) {
        this.vermas = vermas;
    }

    public HolderFactura(View itemView) {
        super(itemView);
        fechaemision = (TextView) itemView.findViewById(R.id.tv_fechaemision);
        estado=(TextView)itemView.findViewById(R.id.tv_estado);
        monto=(TextView)itemView.findViewById(R.id.tv_monto);

        verpedido=(Button) itemView.findViewById(R.id.b_verPedido);
        vermas=(Button) itemView.findViewById(R.id.b_vermas);
           }

}
