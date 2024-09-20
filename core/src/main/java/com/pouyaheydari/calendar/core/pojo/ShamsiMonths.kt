package com.pouyaheydari.calendar.core.pojo

import android.content.Context
import com.pouyaheydari.calendar.core.R

enum class ShamsiMonths(val monthNumber: Int) {
    Farwarding(1),
    Ordibehesht(2),
    Khordad(3),
    Tir(4),
    Mordad(5),
    Shahrivar(6),
    Mehr(7),
    Aban(8),
    Azar(9),
    Dey(10),
    Bahman(11),
    Esfand(12);

    fun getName(context: Context): String = when (monthNumber) {
        1 -> context.getString(R.string.farvardin)
        2 -> context.getString(R.string.ordibehesht)
        3 -> context.getString(R.string.khordad)
        4 -> context.getString(R.string.tir)
        5 -> context.getString(R.string.mordad)
        6 -> context.getString(R.string.shahrivar)
        7 -> context.getString(R.string.mehr)
        8 -> context.getString(R.string.aban)
        9 -> context.getString(R.string.azar)
        10 -> context.getString(R.string.dey)
        11 -> context.getString(R.string.bahman)
        12 -> context.getString(R.string.esfand)
        else -> throw IllegalArgumentException("Month number is grater than 12: $this")
    }
}
