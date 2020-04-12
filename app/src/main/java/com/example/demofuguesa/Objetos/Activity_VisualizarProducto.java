package com.example.demofuguesa.Objetos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.demofuguesa.R;

public class Activity_VisualizarProducto extends AppCompatActivity {
    private TextView precio,cantidadminima,caracteristica,nombre,precioventaminimo;
    private ImageView producto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_producto);
        precio=(TextView)findViewById(R.id.tv_precioVenta);
        cantidadminima=(TextView)findViewById(R.id.tv_cantidadMinima);
        caracteristica=(TextView)findViewById(R.id.tv_caracteristica);
        nombre=(TextView)findViewById(R.id.tv_nombreProducto);
        precioventaminimo=(TextView)findViewById(R.id.tv_precioVentaMinimo);
        producto=(ImageView)findViewById(R.id.i_producto);


        Bundle extras=getIntent().getExtras();
        String sprecio=extras.getString("precio");
        String scaracteristica=extras.getString("caracteristica");
        String scantidadminima=extras.getString("cantidadminima");
        String snombre=extras.getString("nombre");
        String simagen=extras.getString("imagen");
        Glide.with(getApplicationContext()).load(simagen).into(producto);
        precio.setText(sprecio);
        cantidadminima.setText(scantidadminima);
        caracteristica.setText(scaracteristica);
        nombre.setText(snombre);
        precioventaminimo.setText(String.valueOf(Double.parseDouble(sprecio)*Double.parseDouble(scantidadminima)));

    }


}
