package com.example.loginvaltierrez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usuario;
    EditText contraseña;
    Button boton;

    String user = "Beto";
    String contra = "qwerty123";

    int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.usuario);
        boton = (Button) findViewById(R.id.button);
        contraseña = (EditText) findViewById(R.id.constraseña);

        verificarPermisos();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION); startActivity(myIntent);
        }
    }

    private void verificarPermisos() {
        int permiso_paquetes = ContextCompat.checkSelfPermission(this, Manifest.permission.QUERY_ALL_PACKAGES);
        int permiso_foreground = ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE);
        int permiso_overlay = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
        int permiso_tareas = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_TASKS);
        int permiso_procesos = ContextCompat.checkSelfPermission(this, Manifest.permission.KILL_BACKGROUND_PROCESSES);
        if(permiso_paquetes == PackageManager.PERMISSION_GRANTED && permiso_foreground == PackageManager.PERMISSION_GRANTED &&
        permiso_overlay == PackageManager.PERMISSION_GRANTED && permiso_tareas == PackageManager.PERMISSION_GRANTED
        && permiso_procesos == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.QUERY_ALL_PACKAGES, Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_TASKS, Manifest.permission.KILL_BACKGROUND_PROCESSES},REQUEST_CODE);
        }
    }

    //Boton Registrar
    public void Registrar(View view){
        Intent registrar = new Intent(this, RegistrarseActivity.class);
        startActivity(registrar);
    }

    public void Comprobacion(View view){

        if (user.equals(usuario.getText().toString()) && contra.equals(contraseña.getText().toString())){
            Intent bienvenida = new Intent(this, BienvenidaActivity.class);
            startActivity(bienvenida);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("¿Intentar de nuevo?")
                    .setTitle("Datos erroneos")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usuario.setText("");
                            contraseña.setText("");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alerta = builder.create();
            alerta.show();
        }
    }

}