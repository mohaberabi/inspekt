package com.mohaberabi.inspekt.inspektlibrary.inspekt.impl

import com.erabipt.database.erabifit.builder.RoomDatabaseBuilder
import com.erabipt.database.erabifit.dao.HttpLogDao
import com.erabipt.database.erabifit.database.InspektDatabase
import com.mohaberabi.inspekt.inspektlibrary.ui.InspektViewController
import com.mohaberabi.inspekt.inspektlibrary.ui.InspektViewControllerPresenter
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfig
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy
import com.mohaberabi.inspekt.inspektlibrary.inspekt.emptyimpl.EmptyInspekt
import com.mohaberabi.inspekt.inspektlibrary.providers.NotificationConfigProvider
import com.mohaberabi.inspekt.inspektlibrary.providers.ShortcutConfigProvider
import com.mohaberabi.inspekt.notification.IosNotificationManager
import com.mohaberabi.inspekt.shortcut.AppShortcut
import com.mohaberabi.inspekt.shortcut.IosShortcutManager
import platform.UIKit.UIApplicationShortcutItem
import platform.UserNotifications.UNNotification

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GlobalInspekt {
    actual companion object {

        private var iosInstance: Inspekt? = null

        private val inspektViewController by lazy {
            InspektViewController(
                onDismiss = InspektViewControllerPresenter::dismiss,
            )
        }

        actual val sharedInstance: Inspekt
            get() = iosInstance ?: inspektNotInitializedError()

        actual fun configure(
            strategy: InspektConfigStrategy
        ) {
            when (strategy) {
                is InspektConfigStrategy.Actual -> configureActual(strategy)
                InspektConfigStrategy.NoOperation -> configureNoOperation()
            }
        }

        private fun configureActual(
            strategy: InspektConfigStrategy.Actual,
        ) {
            if (iosInstance != null) return
            val dao = setupDatabase()
            val inspekt = InspektImpl(
                notificationManager = IosNotificationManager(),
                dao = dao,
                notificationConfigProvider = NotificationConfigProvider(),
                strategy = strategy
            )
            setupShortcut(shortcutEnabled = strategy.shortcutEnabled)
            iosInstance = inspekt
        }

        private fun configureNoOperation() {
            iosInstance = EmptyInspekt
        }

        private fun setupDatabase(): HttpLogDao {
            val builder = RoomDatabaseBuilder()
            val database = builder.create<InspektDatabase>(DB_NAME).build()
            val dao = database.httpLodDao()
            return dao
        }

        private fun setupShortcut(
            shortcutEnabled: Boolean,
        ) {
            runCatching {
                val shortcutManager = IosShortcutManager()
                if (shortcutEnabled) {
                    val shortCutConfigProvider = ShortcutConfigProvider()
                    val shortCut = AppShortcut(
                        id = INSPEKT,
                        longName = SHORTCUT_SUBTITLE,
                        shortName = SHORTCUT_TITLE,
                        config = shortCutConfigProvider.provideInspektShortcutConfig()
                    )
                    shortcutManager.addShortcut(shortCut)
                } else {
                    shortcutManager.removeShortcutById(INSPEKT)
                }
            }

        }

        fun shortcutOpenedApp(item: UIApplicationShortcutItem) {
            if (item.type == INSPEKT) {
                openInspektView()
            }
        }


        fun notificationClicked(notification: UNNotification) {
            val data = notification.request.content.userInfo
            if (data[INSPEKT] != null) {
                openInspektView()
            }
        }

        private fun openInspektView() {
            InspektViewControllerPresenter.show(inspektViewController)
        }
    }


}