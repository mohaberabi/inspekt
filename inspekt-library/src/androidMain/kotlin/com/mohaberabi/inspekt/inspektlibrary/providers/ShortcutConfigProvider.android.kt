package com.mohaberabi.inspekt.inspektlibrary.providers

import android.content.Context
import android.content.Intent
import androidx.core.graphics.drawable.IconCompat
import com.inspekt.notifiaction.R
import com.mohaberabi.inspekt.inspektlibrary.ui.InspektActivity
import com.mohaberabi.inspekt.inspektlibrary.applyInspekt
import com.mohaberabi.inspekt.inspektlibrary.getAppName
import com.mohaberabi.inspekt.shortcut.AppShortcutConfig

internal actual class ShortcutConfigProvider(
    private val context: Context
) {
    actual fun provideInspektShortcutConfig(): AppShortcutConfig {
        val intent = Intent(context, InspektActivity::class.java)
            .applyInspekt(context.getAppName())
        return AppShortcutConfig(
            intent = intent,
            icon = IconCompat.createWithResource(
                context,
                R.drawable.ic_launcher_foreground
            )
        )
    }
}