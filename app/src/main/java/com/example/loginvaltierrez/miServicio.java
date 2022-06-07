package com.example.loginvaltierrez;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class miServicio extends Service{
    public Context contexto;
    static ArrayList listaAppsBloquear = new ArrayList();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        contexto=miServicio.this.getApplicationContext();
        listaAppsBloquear.add(BloqueoActivity.nombrePaquete);
        String nombrePaquete=BloqueoActivity.nombrePaquete;
        Log.d("Paquete",nombrePaquete);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    String primerPlano=getForegroundApp();
                    Log.d("PrimerPlano", primerPlano);
                    for(int i = 0;i < listaAppsBloquear.size();i++){
                        if (listaAppsBloquear.get(i).equals(primerPlano)){
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexto.startActivity(startMain);
                        }
                    }


                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

        }).start();

        final String CHANNELID = "Foreground Service ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this,CHANNELID)
                    .setContentText("Bloqueado de Apps Activo")
                    .setContentTitle("StayAway")
                    .setSmallIcon(R.drawable.logo);
            startForeground(1,notification.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(CHANNELID)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(1, notification);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Detener el Proceso
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Detectar aplicacion en primer plano
    public String getForegroundApp() {
        String currentApp = "NULL";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        return currentApp;
    }

    //Aqui se borran la app de la lista al presionar un boton para desbloquear
    public void borrarAppBloquear(String appBorrar){
        for(int i = 0;i < listaAppsBloquear.size();i++){
            if (listaAppsBloquear.get(i).equals(appBorrar)){
                listaAppsBloquear.remove(i);
            }
        }
    }


}
