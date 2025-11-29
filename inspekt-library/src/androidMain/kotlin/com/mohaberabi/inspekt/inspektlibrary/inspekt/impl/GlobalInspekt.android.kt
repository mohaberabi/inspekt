package com.mohaberabi.inspekt.inspektlibrary.inspekt.impl

import android.content.Context
import com.erabipt.database.erabifit.builder.RoomDatabaseBuilder
import com.erabipt.database.erabifit.database.InspektDatabase
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfig
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy
import com.mohaberabi.inspekt.inspektlibrary.inspekt.emptyimpl.EmptyInspekt
import com.mohaberabi.inspekt.inspektlibrary.providers.NotificationConfigProvider
import com.mohaberabi.inspekt.inspektlibrary.providers.ShortcutConfigProvider
import com.mohaberabi.inspekt.notification.AndroidInspektNotificationManager
import com.mohaberabi.inspekt.shortcut.AndroidShortcutManager
import com.mohaberabi.inspekt.shortcut.AppShortcut

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GlobalInspekt {
    actual companion object {
        private var androidInstance: Inspekt? = null

        actual val sharedInstance: Inspekt
            get() = androidInstance ?: inspektNotInitializedError()

        actual fun configure(
            strategy: InspektConfigStrategy
        ) {
            when (strategy) {
                is InspektConfigStrategy.Actual -> configureActualImpl(strategy)
                InspektConfigStrategy.NoOperation -> configureNoOperation()
            }
        }

        private fun configureActualImpl(
            strategy: InspektConfigStrategy.Actual,
        ) {
            if (androidInstance != null) return
            val config = strategy.config
            val context = config.context
            val notificationManager = AndroidInspektNotificationManager(context = context)
                .apply { initChannel() }
            val builder = RoomDatabaseBuilder(context)
            val database = builder.create<InspektDatabase>(DB_NAME).build()
            val dao = database.httpLodDao()
            val notificationConfigProvider =
                NotificationConfigProvider(context)
            val inspekt = InspektImpl(
                notificationManager = notificationManager,
                dao = dao,
                notificationConfigProvider = notificationConfigProvider,
                strategy = strategy
            )
            configureShortcut(
                context = context,
                shortcutEnabled = strategy.shortcutEnabled
            )
            androidInstance = inspekt
        }

        private fun configureShortcut(
            context: Context,
            shortcutEnabled: Boolean,
        ) {
            runCatching {
                val manager = AndroidShortcutManager(context)
                if (shortcutEnabled) {
                    val configProvider = ShortcutConfigProvider(context)
                    val config = configProvider.provideInspektShortcutConfig()
                    val shortcut = AppShortcut(
                        id = INSPEKT,
                        longName = SHORTCUT_TITLE,
                        shortName = SHORTCUT_SUBTITLE,
                        config = config
                    )
                    manager.addShortcut(shortcut)
                } else {
                    manager.removeShortcutById(INSPEKT)
                }

            }

        }

        private fun configureNoOperation() {
            androidInstance = EmptyInspekt
        }
    }


}