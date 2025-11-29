package com.mohaberabi.inspekt.inspektlibrary.inspekt.impl

import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfig
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy

internal const val INSPEKT = "inspekt"
internal const val SHORTCUT_TITLE = "Open Inspekt"
internal const val SHORTCUT_SUBTITLE = "to inspect and log your http calls"

internal const val DB_NAME = "${INSPEKT}.db"
internal fun inspektNotInitializedError(): Nothing =
    error("GlobalInspekt instance is not initialized u forgot to call configure or configureNonImpl")

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class GlobalInspekt {
    companion object {
        val sharedInstance: Inspekt
        fun configure(strategy: InspektConfigStrategy)

    }

}



