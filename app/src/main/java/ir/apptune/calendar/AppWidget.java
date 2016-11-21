package ir.apptune.calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * The CLass that widget uses to show data
 */

public class AppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.txt_widget_show_date, pendingIntent);
            CalendarTool calendar = new CalendarTool();
            String s = calendar.getWeekDayStr() + " " + PersianNumberFormatHelper.toPersianNumber(calendar.getIranianDay() + "") + " " +
                    PersianMonthName.getName(calendar.getIranianMonth()) + " " +
                    PersianNumberFormatHelper.toPersianNumber(calendar.getIranianYear() + "");

            remoteViews.setTextViewText(R.id.txt_widget_show_date, s);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);


        }
    }
}
