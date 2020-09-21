package ir.apptune.calendar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ir.apptune.calendar.MainActivity;
import ir.apptune.calendar.R;
import ir.apptune.calendar.base.extensions.IntExtensionsKt;
import ir.apptune.calendar.repository.local.CalendarTool;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Shows Date Sticky Notification that contains Today's data..
 */

public class NotificationActivity extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        CalendarTool cTool = new CalendarTool();
        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), myIntent, 0);
        Notification notif = new NotificationCompat.Builder(context)
                .setContentTitle(cTool.getWeekDayStr() + " " + IntExtensionsKt.toPersianNumber(cTool.getIranianDay())
                        + " " + IntExtensionsKt.toPersianWeekDay(cTool.getIranianMonth(), context) + " " + IntExtensionsKt.toPersianNumber(cTool.getIranianYear()))
                .setContentText(IntExtensionsKt.toPersianNumber(cTool.getGregorianDay()) + " " + IntExtensionsKt.toEnglishMonth(cTool.getGregorianMonth(), context) + " " + IntExtensionsKt.toPersianNumber(cTool.getGregorianYear()))
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

    }
}
