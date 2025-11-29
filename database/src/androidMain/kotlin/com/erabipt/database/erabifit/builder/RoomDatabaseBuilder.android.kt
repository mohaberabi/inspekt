package com.erabipt.database.erabifit.builder

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver

actual class RoomDatabaseBuilder(
    val context: Context,
) {
    actual inline fun <reified T : RoomDatabase> create(
        name: String,
    ): RoomDatabase.Builder<T> {
        val databaseFile = context.getDatabasePath(name)
        return Room.databaseBuilder<T>(
            context = context,
            name = databaseFile.absolutePath
        ).defaultBuilder(driver = AndroidSQLiteDriver())
    }
}