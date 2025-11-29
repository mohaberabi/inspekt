package com.mohaberabi.inspekt.inspektlibrary

import android.content.Context
import android.content.Intent


internal fun Context.getAppName(): String {
    val labelRes = applicationInfo.labelRes
    return if (labelRes != 0) {
        getString(labelRes)
    } else {
        applicationInfo.nonLocalizedLabel.toString()
    }
}


internal fun Intent.applyInspekt(appName: String) = apply {
    action = Intent.ACTION_VIEW
    flags += Intent.FLAG_ACTIVITY_NEW_TASK
    putExtra("root_launcher", appName)
}