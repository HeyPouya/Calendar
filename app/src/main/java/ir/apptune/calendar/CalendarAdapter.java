package ir.apptune.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pouya on 17/11/2016.
 */

public class CalendarAdapter extends BaseAdapter {
    public CalendarAdapter(Context mContext, List<DateModel> dateModels) {
        this.mContext = mContext;
        this.dateModels = dateModels;
    }

    private Context mContext;
    private List<DateModel> dateModels;

    @Override
    public int getCount() {
        return dateModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {

        TextView txt_shamsi;
        TextView txt_miladi;

        ViewHolder(View view) {
            txt_shamsi = (TextView) view.findViewById(R.id.txt_shamsi);
            txt_miladi = (TextView) view.findViewById(R.id.txt_miladi);
        }

        private void fill(int i, List<DateModel> dateModels) {
            txt_shamsi.setText(dateModels.get(i).getDay());
            txt_miladi.setText(dateModels.get(i).getgDay());
            if (dateModels.get(i).getToday())
                txt_shamsi.setTextColor(Color.parseColor("#00ff00"));
            if (dateModels.get(i).getDayofWeek()!= "-" && Integer.parseInt(dateModels.get(i).getDayofWeek()) == 4)
                txt_shamsi.setTextColor(Color.parseColor("#FF4081"));

        }


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.fill(i, dateModels);
        return view;


    }
}
