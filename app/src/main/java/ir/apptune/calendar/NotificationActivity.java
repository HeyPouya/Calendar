package ir.apptune.calendar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Pouya on 18/11/2016.
 */

public class NotificationActivity extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

int day = intent.getIntExtra("IranianDay",0);
int month = intent.getIntExtra("IranianMonth",0);
int year = intent.getIntExtra("IranianYear",0);

        Intent myIntent = new Intent(context, MainActivity.class);
        intent.putExtra("key" , "value") ;
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), myIntent, 0);
        Notification notif = new NotificationCompat.Builder(context)
                .setContentTitle("تاریخ امروز :")
                .setContentText(day+"/"+month+"/"+year)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
        NotificationManager notificationCompatManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationCompatManager.notify(0, notif);



        Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();
        Log.d("LOG","WE ARE IN INTENT");








    }
}
