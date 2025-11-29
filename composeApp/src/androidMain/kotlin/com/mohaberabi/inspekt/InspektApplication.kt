package com.mohaberabi.inspekt

import android.app.Application
import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.GlobalInspekt
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfig
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy

class InspektApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalInspekt.configure(
            strategy = InspektConfigStrategy.Actual(
                config = InspektConfig(this),
                notificationEnabled = true,
                shortcutEnabled = true,
                maxPersistenceEntry = 10
            )
        )
    }
}