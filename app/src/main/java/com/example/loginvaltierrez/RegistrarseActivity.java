package com.example.loginvaltierrez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistrarseActivity extends AppCompatActivity {

    EditText usuariio;
    EditText correo;
    EditText contraseña;
    EditText rep_contraseña;
    EditText boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


    }

    //Metodo para inicio de sesion
    public void Login(View view){
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

    //metodo para registrarse:
    public static void Registrarse(View view){

    }

}