package com.erabipt.database.erabifit.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.erabipt.database.erabifit.convertors.HttpLogConvertor
import com.erabipt.database.erabifit.dao.HttpLogDao
import com.erabipt.database.erabifit.entity.HttpLogEntity

@Database(
    entities = [
        HttpLogEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    HttpLogConvertor::class
)
@ConstructedBy(InspektDatabaseCreator::class)
abstract class InspektDatabase : RoomDatabase() {
    abstract fun httpLodDao(): HttpLogDao
}


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object InspektDatabaseCreator : RoomDatabaseConstructor<InspektDatabase> {
    override fun initialize(): InspektDatabase
}
