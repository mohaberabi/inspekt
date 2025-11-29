package com.erabipt.database.erabifit.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import kotlinx.serialization.json.Json


private val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    explicitNulls = false
}

@ProvidedTypeConverter
class HttpLogConvertor() {
    @TypeConverter
    fun fromString(value: String): HttpLogEntry {
        return json.decodeFromString(string = value, deserializer = HttpLogEntry.serializer())
    }

    @TypeConverter
    fun toString(value: HttpLogEntry): String {
        return json.encodeToString(value)
    }
}
