package com.example.demofuguesa.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Inicio.ListaInicialSinLogin;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrarActivity extends AppCompatActivity {
    private Button registrar;
    private EditText enombre, eapellidopaterno, eapellidomaterno;
    private EditText enumerotelefonico,eusuario,econtraseña;
    boolean existe=false;//verifica que exista la cuenta
    boolean igualdad=false;//verifica que la contraseña y el usuario sean el mismo
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE);
    private List<Cliente> clienteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        final DatabaseReference reference=database.getReference(FirebaseReferences.FUGUESA_REFERENCE+"/"+FirebaseReferences.CLIENTE_REFERENCE);
        enombre=(EditText)findViewById(R.id.e_primernombre);
        eapellidomaterno=(EditText)findViewById(R.id.e_apellidomaterno);
        eapellidopaterno=(EditText)findViewById(R.id.e_apellidopaterno);
        enumerotelefonico=(EditText)findViewById(R.id.e_telefono);
        eusuario=(EditText)findViewById(R.id.e_usuario);
        clienteList=new ArrayList<>();
        econtraseña=(EditText)findViewById(R.id.e_contraseña);
        registrar=(Button)findViewById(R.id.b_registrarse);


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cliente cliente=dataSnapshot.getValue(Cliente.class);
                clienteList.add(cliente);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre=enombre.getText()
                        .toString().toUpperCase();
                String apellidopaterno=eapellidopaterno.getText()
                        .toString().toUpperCase();
                String apellidomaterno=eapellidomaterno.getText()
                        .toString().toUpperCase();
                String numerotelefonico=enumerotelefonico.getText()
                        .toString();//cadena de string de numero
                String usuario=eusuario.getText()
                        .toString();//cadena de string de dni
                String contraseña=econtraseña.getText()
                        .toString();//cadena de string de contraseña
                if( nombre.isEmpty() || apellidopaterno.isEmpty() || apellidomaterno.isEmpty()|| numerotelefonico.isEmpty()|| usuario.isEmpty()||  contraseña.isEmpty())//verifico si están vacios
                {
                    Toast.makeText(RegistrarActivity.this,"complete todos los espacios",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(numerotelefonico.charAt(0)!='9' || numerotelefonico.length()!=9)
                    {
                        Toast.makeText(RegistrarActivity.this,"ingrese un número telefónico válido",Toast.LENGTH_SHORT).show();
                    }

                    else  if(usuario.length()!=8)
                    {
                        Toast.makeText(RegistrarActivity.this,"ingrese un número de dni válido",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            Integer.parseInt(usuario);
                            agregar(determinar_cuenta(clienteList, usuario), nombre, apellidopaterno,
                                    apellidomaterno, numerotelefonico, usuario, contraseña, verificar_contraseña(usuario, contraseña));
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(RegistrarActivity.this,"ingrese un número de dni válido",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public boolean determinar_cuenta(List<Cliente> clientes, String usuario)
    {
        existe=false;
        for (Cliente c: clientes) {
            if(c.getUsuario().equals(usuario) ) {
                Toast.makeText(RegistrarActivity.this,"El usuario "+c.getUsuario().toString()+" ya ha sido registrado previamente",Toast.LENGTH_SHORT).show();
                existe=true;

            }
        }
        return existe;
    }

    public void agregar(boolean existe,String nombre,String apellidopaterno,String apellidomaterno,String numerotelefonico,String usuario,String contraseña,boolean igualdad)
    {
        if(!existe && igualdad)
        {
           final Cliente cliente=Cliente.getInstance();
            cliente.setNombre(nombre);
            cliente.setApellidopaterno(apellidopaterno);
            cliente.setApellidomaterno(apellidomaterno);
            cliente.setNumerotelefonico(numerotelefonico);
            cliente.setUsuario(usuario);
            cliente.setContraseña(contraseña);
            cliente.setSaldoAsignado(0);
            cliente.setSaldoRestante(0);
            //Cliente cliente=new Cliente(nombre,apellidopaterno,apellidomaterno,numerotelefonico,usuario,contraseña);
            reference.child(FirebaseReferences.CLIENTE_REFERENCE).push().setValue(cliente);

            //Toast.makeText(RegistrarActivity.this,"Usuario "+nombre+" "+ apellidopaterno+" registrado exitósamente",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder= new AlertDialog.Builder(RegistrarActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Registro exitoso");
            builder.setMessage("Se registró correctamente");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nextActivityInicio();
                    cliente.setUsuario(null);
                    dialog.cancel();
                }
            });
            builder.show();

        }
    }

    final public boolean verificar_contraseña(String usuario, String contraseña)
    {
        igualdad=false;
        if(usuario.equals(contraseña))
        {
            igualdad=true;
        }
        else { Toast.makeText(RegistrarActivity.this,
                "El usuario y contraseña no coinciden",Toast.LENGTH_SHORT).show(); }
        return igualdad;
    }

    private void nextActivityInicio() {
        startActivity(new Intent(RegistrarActivity.this, ListaInicialSinLogin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        onBackPressed();
        finish();
    }
}
