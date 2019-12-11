package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private DownloadBinder mBinder=new DownloadBinder();
    private static final String ID="channel_1";
    private static final String NAME="前台服务";

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","startDownload");
        }
        public int getProgress(){
            Log.d("MyService","getProgress");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    @Override
    public void onCreate() { //前台服务
        super.onCreate();
        Log.d("MyService","onCreate MyService");
        Intent intent=new Intent(this,MainActivity.class);
        NotificationManager manager=(NotificationManager)getSystemService (NOTIFICATION_SERVICE);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        NotificationChannel channel=new NotificationChannel(ID,NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel (channel);
        Notification notification=new NotificationCompat.Builder(this,ID)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService","onStartCommand MyService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService","onDestroy MyService");
    }
}
