package com.example.demofuguesa.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demofuguesa.R;

public class HolderProducto extends RecyclerView.ViewHolder {
    private TextView nombreProducto;
    private ImageView fotoPerfil;
    private Button aumentar;
    private Button reducir;
    private Button mostrar;
    private Button carrito;

    public Button getMostrarcarrito() {
        return mostrarcarrito;
    }

    public void setMostrarcarrito(Button mostrarcarrito) {
        this.mostrarcarrito = mostrarcarrito;
    }

    private Button mostrarcarrito;

    public Button getAumentar() {
        return aumentar;
    }

    public void setAumentar(Button aumentar) {
        this.aumentar = aumentar;
    }

    public Button getReducir() {
        return reducir;
    }

    public void setReducir(Button reducir) {
        this.reducir = reducir;
    }

    public Button getMostrar() {
        return mostrar;
    }

    public void setMostrar(Button mostrar) {
        this.mostrar = mostrar;
    }

    public Button getCarrito() {
        return carrito;
    }

    public void setCarrito(Button carrito) {
        this.carrito = carrito;
    }

    public TextView getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(TextView nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public ImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(ImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public HolderProducto(View itemView) {
        super(itemView);
        nombreProducto = (TextView) itemView.findViewById(R.id.t_nproducto);
        fotoPerfil = (ImageView) itemView.findViewById(R.id.i_producto);
        aumentar=(Button) itemView.findViewById(R.id.b_aumentar);
        reducir=(Button)itemView.findViewById(R.id.b_reducir);
        mostrar=(Button)itemView.findViewById(R.id.b_mostrar);
        carrito=(Button)itemView.findViewById(R.id.b_carrito);
        mostrarcarrito=(Button)itemView.findViewById(R.id.b_vercarrito);
    }

}