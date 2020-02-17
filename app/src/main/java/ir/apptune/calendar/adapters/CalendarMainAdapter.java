package ir.apptune.calendar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.calendar.R;
import ir.apptune.calendar.ResourceUtils;
import ir.apptune.calendar.models.DateModel;

import static ir.apptune.calendar.R.id.txt_shamsi;


public class CalendarMainAdapter extends RecyclerView.Adapter<CalendarMainAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<DateModel> dateModels;
    LayoutInflater inflater;

    public CalendarMainAdapter(Context mContext, List<DateModel> dateModels) {
        this.mContext = mContext;
        this.dateModels = dateModels;
        inflater = LayoutInflater.from(mContext);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtShamsi.setText(dateModels.get(position).getDay());
        holder.txtMiladi.setText(dateModels.get(position).getgDay());
        ResourceUtils resourceUtils = new ResourceUtils(mContext);

        //Changes Today Background
        if (dateModels.get(position).getToday()) {
            holder.txtShamsi.setTextColor(Color.parseColor("#ffffff"));
            holder.txtMiladi.setTextColor(Color.parseColor("#ffffff"));

            holder.layoutDays.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            holder.txtShamsi.setTextColor(Color.parseColor("#000000"));
            holder.txtMiladi.setTextColor(Color.parseColor("#000000"));
            holder.layoutDays.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        //Changes color of holidays to RedPink
        if (dateModels.get(position).getDay() != "-") {
            int persianTemp = Integer.parseInt(dateModels.get(position).getMonth()) * 100;
            persianTemp += Integer.parseInt(dateModels.get(position).getDay());
            if (resourceUtils.vacationP.containsKey(persianTemp))
                holder.txtShamsi.setTextColor(Color.parseColor("#FF4081"));
        }
        if (dateModels.get(position).getDayofWeek() != "-" && Integer.parseInt(dateModels.get(position).getDayofWeek()) == 4)
            holder.txtShamsi.setTextColor(Color.parseColor("#FF4081"));


    }

    @Override
    public int getItemCount() {
        return dateModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(txt_shamsi)
        TextView txtShamsi;
        @BindView(R.id.txt_miladi)
        TextView txtMiladi;
        @BindView(R.id.layout_days)
        LinearLayout layoutDays;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
