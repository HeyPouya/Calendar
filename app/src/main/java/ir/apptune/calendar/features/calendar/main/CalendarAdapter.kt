package ir.apptune.calendar.features.calendar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.calendar.R
import ir.apptune.calendar.databinding.CalendarItemBinding
import ir.apptune.calendar.features.calendar.main.CalendarAdapter.CalendarViewHolder
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.utils.EMPTY_DATE
import ir.apptune.calendar.utils.extensions.toPersianNumber

/**
 * Adapter to show the calendar days
 */
class CalendarAdapter(val clickListener: (CalendarModel) -> Unit) : ListAdapter<CalendarModel, CalendarViewHolder>(CalendarDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) =
            holder.onBind(getItem(position))

    inner class CalendarViewHolder(private val binding: CalendarItemBinding, containerView: View) :
            RecyclerView.ViewHolder(containerView) {

        /**
         * Binds views via ViewBinding
         */
        fun onBind(dateModel: CalendarModel) {
            with(dateModel) {
                with(binding) {
                    txtIranianDate.isVisible = iranianDay != EMPTY_DATE
                    txtGregorianDate.isVisible = iranianDay != EMPTY_DATE
                    if (iranianDay == EMPTY_DATE) return

                    itemView.setOnClickListener { clickListener(dateModel) }
                    txtIranianDate.text = iranianDay.toPersianNumber()
                    txtGregorianDate.text = gDay.toString()
                    txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.primaryTextColor))

                    if (today) {
                        txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                        txtGregorianDate.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                        cardDays.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
                    }

                    if (isHoliday)
                        txtIranianDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
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