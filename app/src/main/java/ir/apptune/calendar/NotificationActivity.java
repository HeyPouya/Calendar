package ir.apptune.calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Shows Date Notification.
 */

public class NotificationActivity extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        CalendarTool cTool = new CalendarTool();
        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), myIntent, 0);
        Notification notif = new NotificationCompat.Builder(context)
                .setContentTitle(cTool.getWeekDayStr() + " " + cTool.getIranianDay()
                        + " " + PersianMonthName.getName(cTool.getIranianMonth()) + " " + cTool.getIranianYear())
                .setContentText(cTool.getGregorianDay() + " " + EnglishMonthName.getName(cTool.getGregorianMonth()) + " " + cTool.getGregorianYear())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .setWhen(0)
                .setPriority(1)
                .build();
        NotificationManager notificationCompatManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationCompatManager.notify(0, notif);

        Log.d("LOG", "WE ARE IN");


    }
}
