package ir.apptune.calendar.features.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.calendar.R
import ir.apptune.calendar.features.calendar.CalendarAdapter.CalendarViewHolder
import ir.apptune.calendar.utils.extensions.toPersianNumber
import ir.apptune.calendar.pojo.CalendarModel
import kotlinx.android.synthetic.main.calendar_item.view.*

class CalendarAdapter(val clickListener: (CalendarModel) -> Unit) : ListAdapter<CalendarModel, CalendarViewHolder>(CalendarDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) =
            holder.onBind(getItem(position))

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(dateModel: CalendarModel) {
            with(itemView) {
                with(dateModel) {
                    if (iranianDay == -1) {
                        txtIranianDate.visibility = View.INVISIBLE
                        txtGregorianDate.visibility = View.INVISIBLE
                        return
                    }
                    setOnClickListener { clickListener(dateModel) }
                    txtIranianDate.text = iranianDay.toPersianNumber()
                    txtGregorianDate.text = gDay.toString()


                    if (today) {
                        txtIranianDate.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                        txtGregorianDate.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                        cardDays.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    }

                    if (isHoliday)
                        txtIranianDate.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                }
            }
        }

    }
}

private class CalendarDiffUtils : DiffUtil.ItemCallback<CalendarModel>() {

    override fun areItemsTheSame(oldItem: CalendarModel, newItem: CalendarModel) =
            oldItem.iranianDay == newItem.iranianDay && oldItem.iranianMonth == newItem.iranianMonth
                    && oldItem.iranianYear == newItem.iranianYear

    override fun areContentsTheSame(oldItem: CalendarModel, newItem: CalendarModel) =
            oldItem.iranianDay == newItem.iranianDay && oldItem.iranianMonth == newItem.iranianMonth
                    && oldItem.iranianYear == newItem.iranianYear
}