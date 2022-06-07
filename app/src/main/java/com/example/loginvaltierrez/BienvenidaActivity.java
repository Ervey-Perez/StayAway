package com.example.loginvaltierrez;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BienvenidaActivity extends AppCompatActivity {

    ListView apps;
    ArrayList lista_apps;
    ArrayList lista_packages;
    TextView app;
    static String nombre_app;
    static String paquete_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida2);
        apps = findViewById(R.id.lista_apps);
        lista_apps = new ArrayList<>();
        lista_packages = new ArrayList<>();
        app = findViewById(R.id.textView);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void obtenerApps(View view){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> paquetes = getPackageManager().queryIntentActivities(intent,0);
        for (int i = 0; i<paquetes.size(); i++) {
            ResolveInfo infoPaquetes = paquetes.get(i);

            String x = infoPaquetes.loadLabel(getPackageManager()).toString();
            String y = infoPaquetes.activityInfo.packageName;

            if (!infoPaquetes.activityInfo.packageName.equals("com.example.loginvaltierrez")){
                lista_apps.add(infoPaquetes.loadLabel(getPackageManager()).toString());
                lista_packages.add(infoPaquetes.activityInfo.packageName);
            }

            Log.e("Nombre + paquete", x+":"+y);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista_apps);
        apps.setAdapter(adapter);

        apps.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(BienvenidaActivity.this, BloqueoActivity.class);
                    intent.putExtra("objetoData", apps.getItemIdAtPosition(position));
                    int i = Math.toIntExact(apps.getItemIdAtPosition(position));
                    nombre_app = (String) lista_apps.get(i);
                    paquete_app = (String) lista_packages.get(i);
                    startActivity(intent);
            }
        });

    }

    //Metodo para inicio de sesion
    public void Login(View view){
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }


}