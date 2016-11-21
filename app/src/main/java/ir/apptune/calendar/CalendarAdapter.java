package ir.apptune.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * This Adapter sets data of calendar on the main page.
 */

class CalendarAdapter extends BaseAdapter {
    CalendarAdapter(Context mContext, List<DateModel> dateModels) {
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
        LinearLayout layout_days;


        ViewHolder(View view) {
            txt_shamsi = (TextView) view.findViewById(R.id.txt_shamsi);
            txt_miladi = (TextView) view.findViewById(R.id.txt_miladi);
            layout_days = (LinearLayout) view.findViewById(R.id.layout_days);
        }

        private void fill(int i, List<DateModel> dateModels) {
            txt_shamsi.setText(dateModels.get(i).getDay());
            txt_miladi.setText(dateModels.get(i).getgDay());
            ResourceUtils resourceUtils = new ResourceUtils(mContext);
            if (dateModels.get(i).getToday()) {
                txt_shamsi.setTextColor(Color.parseColor("#ffffff"));
                txt_miladi.setTextColor(Color.parseColor("#ffffff"));
                layout_days.setBackgroundResource(R.drawable.today_background);
            } else {
                txt_shamsi.setTextColor(Color.parseColor("#000000"));
                txt_miladi.setTextColor(Color.parseColor("#000000"));
                layout_days.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            if (dateModels.get(i).getDay() != "-") {
                int persianTemp = Integer.parseInt(dateModels.get(i).getMonth()) * 100;
                persianTemp += Integer.parseInt(dateModels.get(i).getDay());
                if (resourceUtils.vacationP.containsKey(persianTemp))
                    txt_shamsi.setTextColor(Color.parseColor("#FF4081"));
            }
            if (dateModels.get(i).getDayofWeek() != "-" && Integer.parseInt(dateModels.get(i).getDayofWeek()) == 4)
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
