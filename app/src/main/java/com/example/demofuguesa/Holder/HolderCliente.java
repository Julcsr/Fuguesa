package com.example.demofuguesa.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.R;


public class HolderCliente extends RecyclerView.ViewHolder {
    private TextView nombreCliente;
    private TextView apellidoPaternoCliente;
    private TextView saldoRestante;
    private TextView saldoAsignado;
    private Button verPedidos;
    private Button pagarFactura;

    public Button getVerPedidos() {
        return verPedidos;
    }

    public void setVerPedidos(Button verPedidos) {
        this.verPedidos = verPedidos;
    }

    public Button getPagarFactura() {
        return pagarFactura;
    }

    public void setPagarFactura(Button pagarFactura) {
        this.pagarFactura = pagarFactura;
    }

    public TextView getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(TextView saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public TextView getSaldoAsignado() {
        return saldoAsignado;
    }

    public void setSaldoAsignado(TextView saldoAsignado) {
        this.saldoAsignado = saldoAsignado;
    }


    public TextView getApellidoPaternoCliente() {
        return apellidoPaternoCliente;
    }

    public void setApellidoPaternoCliente(TextView apellidoPaternoCliente) {
        this.apellidoPaternoCliente = apellidoPaternoCliente;
    }

    public TextView getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(TextView nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public TextView getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(TextView apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    private TextView apellidoMaterno;


    public HolderCliente(View itemView) {
        super(itemView);
        nombreCliente = (TextView) itemView.findViewById(R.id.tv_primernombre);
        apellidoPaternoCliente=(TextView)itemView.findViewById(R.id.tv_apellidopaterno);
        apellidoMaterno=(TextView)itemView.findViewById(R.id.tv_apellidomaterno);
        saldoAsignado=(TextView)itemView.findViewById(R.id.tv_montoAsignado);
        saldoRestante=(TextView)itemView.findViewById(R.id.tv_saldorestante);
        verPedidos=(Button)itemView.findViewById(R.id.b_verPedidos);
        pagarFactura=(Button)itemView.findViewById(R.id.b_realizarPago);
           }

}
