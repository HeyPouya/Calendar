package com.pouyaheydari.calendar.core.pojo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventResponse(val events: List<EventItem>)

@JsonClass(generateAdapter = true)
data class EventItem(val month: Int, val day: Int, val holiday: Boolean, val title: String)