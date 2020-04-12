package com.example.demofuguesa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demofuguesa.Entidades.Empleado;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView nombre,ap1,ap2,numero,dni;
    Button b;
    Empleado empleado=Empleado.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.boton);
        dni=(TextView)findViewById(R.id.dni);
        nombre=(TextView)findViewById(R.id.nombre);
        ap1=(TextView)findViewById(R.id.apellido1);
        ap2=(TextView)findViewById(R.id.apellido2);
        numero=(TextView)findViewById(R.id.numero);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empleado.setUsuario(dni.getText().toString());
                empleado.setNombre(nombre.getText().toString());
                empleado.setApellidopaterno(ap1.getText().toString());
                empleado.setApellidomatern(ap2.getText().toString());
                empleado.setNumerotelefonico(numero.getText().toString());
                empleado.setContraseña(dni.getText().toString());
                reference.child(FirebaseReferences.EMPLEADO_REFERENCE).push().setValue(empleado);
            }
        });

    }
}
