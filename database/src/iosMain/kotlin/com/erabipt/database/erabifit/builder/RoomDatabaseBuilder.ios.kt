package com.erabipt.database.erabifit.builder

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

actual class RoomDatabaseBuilder() {
    actual inline fun <reified T : RoomDatabase> create(
        name: String,
    ): RoomDatabase.Builder<T> {
        val dbFilePath = "$documentsDirectory/$name"
        val builder = Room.databaseBuilder<T>(name = dbFilePath)
        return builder.defaultBuilder(driver = BundledSQLiteDriver())
    }
}

@OptIn(ExperimentalForeignApi::class)
val documentsDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = true,
    error = null,
)?.path ?: NSHomeDirectory()