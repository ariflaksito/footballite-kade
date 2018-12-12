package net.ariflaksito.footballlite.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(str: String?): String {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy, HH:mm")
    formatter.timeZone = TimeZone.getDefault()

    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ")
    return formatter.format(date.parse(str))
}

fun formatDate(str: String?): String {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy")
    formatter.timeZone = TimeZone.getDefault()

    val date = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(date.parse(str))
}

fun toMillis(strDate: String?, strTime: String?): Long {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX", Locale.US)
    val date = simpleDateFormat.parse("$strDate $strTime")
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.timeInMillis
}
