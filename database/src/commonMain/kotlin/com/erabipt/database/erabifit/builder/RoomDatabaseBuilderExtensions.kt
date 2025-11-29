package com.erabipt.database.erabifit.builder

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import com.erabipt.database.erabifit.convertors.HttpLogConvertor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal typealias AnyRoomDb<T> = RoomDatabase.Builder<T>


inline fun <reified T : RoomDatabase> AnyRoomDb<T>.defaultBuilder(
    driver: SQLiteDriver
): AnyRoomDb<T> = setQueryCoroutineContext(Dispatchers.IO)
    .setDriver(driver)
    .addTypeConverter(HttpLogConvertor())
