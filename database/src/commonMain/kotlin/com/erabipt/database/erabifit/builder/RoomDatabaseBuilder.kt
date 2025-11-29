package com.erabipt.database.erabifit.builder

import androidx.room.RoomDatabase


expect class RoomDatabaseBuilder {
    inline fun <reified T : RoomDatabase> create(
        name: String
    ): RoomDatabase.Builder<T>
}
