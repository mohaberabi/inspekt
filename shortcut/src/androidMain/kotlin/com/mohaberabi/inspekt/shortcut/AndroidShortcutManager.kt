package com.mohaberabi.inspekt.shortcut

import android.content.Context
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat


class AndroidShortcutManager(
    private val context: Context,
) : AppShortcutManager {
    override fun addShortcut(shortcut: AppShortcut) {
        val icon = shortcut.config.icon
        val intent = shortcut.config.intent
        val shortcut = ShortcutInfoCompat.Builder(context, shortcut.id)
            .setShortLabel(shortcut.shortName)
            .setLongLabel(shortcut.longName)
            .setIcon(icon)
            .setIntent(intent)
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
    }

    override fun removeShortcutById(id: String) {
        ShortcutManagerCompat.removeDynamicShortcuts(context, listOf(id))
    }


//    private fun addPinnedShortcut(context: Context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val manager = requireNotNull(context.getSystemService<ShortcutManager>())
//            if (manager.pinnedShortcuts.any { it.id == "pinned" }) return
//
//            val icon = IconCompat.createWithResource(context, com.mohaberabi.inspekt.R.drawable.ic_launcher_foreground)
//            val intent = Intent(context, MainActivity::class.java)
//                .apply { action = Intent.ACTION_VIEW; }
//            val shortcut = ShortcutInfo.Builder(context, "pinned")
//                .setShortLabel("send me message")
//                .setLongLabel("send me message here ")
//                .setIcon(icon.toIcon(context))
//                .setIntent(intent)
//                .build()
//
//
//            if (manager.isRequestPinShortcutSupported) {
//                val shortcutIntent = manager.createShortcutResultIntent(shortcut)
//                val pendingIntent = PendingIntent.getBroadcast(
//                    context,
//                    1,
//                    shortcutIntent,
//                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                )
//                manager.requestPinShortcut(shortcut, pendingIntent.intentSender)
//            }
//        }
//    }

}