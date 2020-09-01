package ir.apptune.calendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.calendar.R
import ir.apptune.calendar.adapters.CalendarAdapter.MyViewHolder
import ir.apptune.calendar.pojo.DateModel
import kotlinx.android.synthetic.main.grid_view_item.view.*

class CalendarAdapter(private val dateModels: List<DateModel>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_view_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.onBind(dateModels[position])

    override fun getItemCount() = dateModels.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(dateModel: DateModel) = with(itemView) {
            with(dateModel) {
                txtIranianDate.text = day
                txtGregorianDate.text = gDay

                if (today) {
                    txtIranianDate.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    txtGregorianDate.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    layoutDays.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }

                if (isHoliday)
                    txtIranianDate.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            }
        }

    }
}