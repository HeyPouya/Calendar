package ir.apptune.calendar.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ir.apptune.calendar.CalendarTool;
import ir.apptune.calendar.EnglishMonthName;
import ir.apptune.calendar.MainActivity;
import ir.apptune.calendar.PersianMonthName;
import ir.apptune.calendar.PersianNumberFormatHelper;
import ir.apptune.calendar.R;

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
            remoteViews.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);
            CalendarTool calendar = new CalendarTool();
            String ps = calendar.getWeekDayStr() + " " + PersianNumberFormatHelper.toPersianNumber(calendar.getIranianDay() + "") + " " +
                    PersianMonthName.getName(calendar.getIranianMonth()) + " " +
                    PersianNumberFormatHelper.toPersianNumber(calendar.getIranianYear() + "");
            String ms = PersianNumberFormatHelper.toPersianNumber(calendar.getGregorianDay() + "") + " " + EnglishMonthName.getName(calendar.getGregorianMonth()) + " " +
                    PersianNumberFormatHelper.toPersianNumber(calendar.getGregorianYear() + "");
            remoteViews.setTextViewText(R.id.txt_widget_show_persian_date, ps);
            remoteViews.setTextViewText(R.id.txt_widget_show_miladi_date, ms);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);


        }
    }
}
