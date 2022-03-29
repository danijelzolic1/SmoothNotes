package se.zolda.smoothnotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmoothNoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}