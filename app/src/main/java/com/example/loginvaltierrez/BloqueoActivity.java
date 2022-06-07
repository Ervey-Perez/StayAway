package com.example.loginvaltierrez;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginvaltierrez.BienvenidaActivity;

public class BloqueoActivity extends AppCompatActivity {


    TextView nombre_app;
    TextView nombre_paquete;
    static String nombrePaquete;
    private Intent myService;
    Button bloquear;
    Button desbloquear;
    miServicio objServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo);

        nombre_app = findViewById(R.id.nombre_app);
        nombre_paquete = findViewById(R.id.nombre_paquete);

        nombre_app.setText(BienvenidaActivity.nombre_app);
        nombre_paquete.setText(BienvenidaActivity.paquete_app);
        bloquear= findViewById(R.id.botonBloquear);
        desbloquear= findViewById(R.id.botonDesbloquear);
        myService = new Intent(this, miServicio.class);

        for(int i = 0;i < miServicio.listaAppsBloquear.size();i++){
            if (miServicio.listaAppsBloquear.isEmpty()==true){
                bloquear.setVisibility(View.VISIBLE);
                desbloquear.setVisibility(View.INVISIBLE);
                Log.e("Primer if", "true");
            }else if (miServicio.listaAppsBloquear.get(i).equals(nombre_paquete.getText().toString())){
                Log.e("Segundo if", "true");
                bloquear.setVisibility(View.INVISIBLE);
                desbloquear.setVisibility(View.VISIBLE);
            }/*else if (i==(miServicio.listaAppsBloquear.size())-1){
                bloquear.setVisibility(View.VISIBLE);
                desbloquear.setVisibility(View.INVISIBLE);
                Log.e("Tercer if", "true");

            }*/

        }

    }

    public void bloqueoAplicaciones(View view){
        bloquear.setVisibility(View.GONE);
        desbloquear.setVisibility(View.VISIBLE);
        nombrePaquete = nombre_paquete.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(myService);
        } else {
            startService(myService);
        }

    }

    public void desbloqueoAplicaciones(View view){
        desbloquear.setVisibility(View.GONE);
        bloquear.setVisibility(View.VISIBLE);
        nombrePaquete = nombre_paquete.getText().toString();
        objServicio = new miServicio();
        objServicio.borrarAppBloquear(nombrePaquete);
    }

}