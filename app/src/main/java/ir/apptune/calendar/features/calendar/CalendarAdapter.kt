package ir.apptune.calendar.features.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.calendar.R
import ir.apptune.calendar.features.calendar.CalendarAdapter.CalendarViewHolder
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.utils.extensions.toPersianNumber
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.calendar_item.*

class CalendarAdapter(val clickListener: (CalendarModel) -> Unit) : ListAdapter<CalendarModel, CalendarViewHolder>(CalendarDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) =
            holder.onBind(getItem(position))

    inner class CalendarViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun onBind(dateModel: CalendarModel) {
            with(dateModel) {
                txtIranianDate.isVisible = iranianDay != -1
                txtGregorianDate.isVisible = iranianDay != -1
                if (iranianDay == -1) return

                itemView.setOnClickListener { clickListener(dateModel) }
                txtIranianDate.text = iranianDay.toPersianNumber()
                txtGregorianDate.text = gDay.toString()
                txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.primaryTextColor))

                if (today) {
                    txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    txtGregorianDate.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    cardDays.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
                }

                if (isHoliday)
                    txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
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