package com.pouyaheydari.calendar.core.di

import android.app.Application
import com.pouyaheydari.calendar.core.R
import com.pouyaheydari.calendar.core.pojo.EventItem
import com.pouyaheydari.calendar.core.pojo.EventResponse
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventsModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Named("shamsi_events")
    @Provides
    @Singleton
    fun provideShamsiEvents(app: Application, moshi: Moshi): List<EventItem> {
        val events = app.resources
            .openRawResource(R.raw.shamsi_events)
            .bufferedReader()
            .readLines()
            .joinToString(separator = "")
        return moshi.adapter(EventResponse::class.java).fromJson(events)?.events.orEmpty()
    }

    @Named("gregorian_events")
    @Provides
    @Singleton
    fun provideGregorianEvents(app: Application, moshi: Moshi): List<EventItem> {
        val events = app.resources
            .openRawResource(R.raw.gregorian_events)
            .bufferedReader()
            .readLines()
            .joinToString(separator = "")
        return moshi.adapter(EventResponse::class.java).fromJson(events)?.events.orEmpty()
    }

    @Named("hijri_events")
    @Provides
    @Singleton
    fun provideHijriEvents(app: Application, moshi: Moshi): List<EventItem> {
        val events = app.resources
            .openRawResource(R.raw.hijri_events)
            .bufferedReader()
            .readLines()
            .joinToString(separator = "")
        return moshi.adapter(EventResponse::class.java).fromJson(events)?.events.orEmpty()
    }
}