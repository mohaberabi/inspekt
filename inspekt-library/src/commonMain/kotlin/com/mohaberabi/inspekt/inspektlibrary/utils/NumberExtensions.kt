package com.mohaberabi.inspekt.inspektlibrary.utils


const val ONE_KB = 1024
const val ONE_MB = ONE_KB * ONE_KB
const val ONE_GB = ONE_MB * ONE_KB
const val ONE_SECOND = 1000L
const val ONE_MINUTE = 60 * ONE_SECOND
const val ONE_HOUR = 60 * ONE_MINUTE

fun Long.sizeReadable(): String {
    return when {
        this < ONE_KB -> "$this B"
        this < ONE_MB -> (toDouble() / ONE_KB).stringAsFixed(suffix = "KB")
        this < ONE_GB -> (toDouble() / ONE_MB).stringAsFixed(suffix = "MB")
        else -> (toDouble() / ONE_GB).stringAsFixed(suffix = "GB")
    }
}

fun Long.timeReadable(): String {
    return when {
        this < ONE_SECOND -> "$this ms"
        this < ONE_MINUTE -> (this / ONE_SECOND).stringAsFixed(suffix = "s")
        this < ONE_HOUR -> (this / ONE_MINUTE).stringAsFixed(suffix = "m")
        else -> (this / ONE_HOUR).stringAsFixed(suffix = "hr")
    }
}

fun Number.stringAsFixed(
    toTake: Int = 2,
    suffix: String = ""
): String {
    val string = toString()
    if (string.isBlank()) return ""
    val afterDot = string.split(".")
    if (afterDot.size < 2) return "$string $suffix"
    val first = afterDot[0]
    val second = afterDot[1].take(toTake)
    return "${first}.${second} ${suffix}"
}