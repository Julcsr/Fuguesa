package com.example.demofuguesa.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demofuguesa.Entidades.Cliente;
import com.example.demofuguesa.Entidades.Empleado;
import com.example.demofuguesa.Inicio.ListaInicial;
import com.example.demofuguesa.Objetos.FirebaseReferences;
import com.example.demofuguesa.Objetos.ListaCliente;
import com.example.demofuguesa.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    boolean igualdad = false;//verifica que la contraseña y el usuario sean el mismo
    private EditText eusuario, econtraseña;
    private Button bingresar;
    boolean empleado = false;//verifica si es empleado=true o cliente empleado=false
    boolean existe = false;//verifica que exista la cuenta
    private List<Cliente> clienteList;
    private List<Empleado> empleadoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.CLIENTE_REFERENCE);
        DatabaseReference empleadoReference = database.getReference(FirebaseReferences.FUGUESA_REFERENCE + "/" + FirebaseReferences.EMPLEADO_REFERENCE);
        eusuario = (EditText) findViewById(R.id.e_usuario);
        econtraseña = (EditText) findViewById(R.id.e_contraseña);
        bingresar = (Button) findViewById(R.id.b_ingresar);
        clienteList = new ArrayList<>();
        empleadoList = new ArrayList<>();
        //recorer cliente
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cliente cliente = dataSnapshot.getValue(Cliente.class);
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

        empleadoReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Empleado empleado = dataSnapshot.getValue(Empleado.class);
                empleadoList.add(empleado);
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


        bingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (econtraseña.toString().isEmpty() || eusuario.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "complete todos los espacios", Toast.LENGTH_SHORT).show();
                } else {
                    String usuario = eusuario.getText()
                            .toString();//cadena de string de dni
                    String contraseña = econtraseña.getText()
                            .toString();
                    if (usuario.length() == 8 && contraseña.length() == 8) {
                        logear(verificar_contraseña(usuario, contraseña), verificar_cuenta(clienteList, usuario, contraseña), usuario, verificar_usuario(clienteList, usuario, empleadoList));
                    } else {
                        Toast.makeText(LoginActivity.this, "usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    final void logear(boolean verificar_contraseña, boolean verificar_cuenta, String usuario, boolean verificar_usuario) {

        if (verificar_cuenta || verificar_usuario) {
            if (verificar_contraseña) {


                if (verificar_usuario == false) {
                    Cliente cliente=Cliente.getInstance();
                    cliente.setUsuario(usuario);

                    for (Cliente c: clienteList) {
                        if(c.getUsuario().equals(usuario)) {
                            cliente.setNombre(c.getNombre());
                            cliente.setApellidopaterno(c.getApellidopaterno());
                            cliente.setApellidomaterno(c.getApellidomaterno());
                            cliente.setSaldoAsignado(c.getSaldoAsignado());
                            cliente.setSaldoRestante(c.getSaldoRestante());
                        }
                    }

                    nextActivityInicio();
                } else {
                    Empleado empleado=Empleado.getInstance();
                    empleado.setUsuario(usuario);
                    nextActivityListaClientes();
                }

            } else {
                Toast.makeText(LoginActivity.this, "usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(LoginActivity.this, "La cuenta no existe", Toast.LENGTH_SHORT).show();
        }
    }

    final public boolean verificar_cuenta(List<Cliente> clientes, String usuario, String contraseña) {
        existe = false;
        for (Cliente c : clientes) {

            try {

                if (c.getUsuario().equals(usuario)) {
                    existe = true;
                }
            } catch (Exception e) {
            }
        }
        return existe;
    }

    final public boolean verificar_contraseña(String usuario, String contraseña) {
        igualdad = false;
        if (usuario.equals(contraseña)) {
            igualdad = true;
        }


        return igualdad;
    }

    final boolean verificar_usuario(List<Cliente> clientes, String usuario, List<Empleado> empleados)//verifica que sea empleado o cliente
    {
        empleado = false;
        for (Cliente c : clientes) {

            try {

                if (c.getUsuario().equals(usuario)) {
                    empleado = false;
                }
            } catch (Exception e) {
            }
        }

        for (Empleado c : empleados) {

            try {

                if (c.getUsuario().equals(usuario)) {
                    empleado = true;
                }
            } catch (Exception e) {
            }
        }

        return empleado;
    }

    private void nextActivityInicio() {
        startActivity(new Intent(LoginActivity.this, ListaInicial.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        onBackPressed();
        finish();
    }

    private void nextActivityListaClientes() {
        startActivity(new Intent(LoginActivity.this, ListaCliente.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        onBackPressed();
        finish();
    }
}