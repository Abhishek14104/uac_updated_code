package com.ccextractor.ultimate_alarm_clock.communication

import com.ccextractor.ultimate_alarm_clock.AlarmModel
import java.text.SimpleDateFormat
import java.util.*

fun MinimalAlarmDTO.toAlarmModel(): AlarmModel {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = formatter.parse(time) ?: Date()
    val calendar = Calendar.getInstance().apply { time = date }

    val minutesSinceMidnight =
            calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    val alarmIdStr = "watch_$id"

    return AlarmModel(
            id = id,
            minutesSinceMidnight = minutesSinceMidnight,
            alarmTime = time,
            days = days.joinToString(","), // Convert list to comma string
            isOneTime = isOneTime,
            activityMonitor = 0,
            activityInterval = 0,
            isWeatherEnabled = 1,
            weatherTypes = "",
            weatherConditionType = 0,
            activityConditionType = 0,
            isLocationEnabled = if (isLocationEnabled) 1 else 0,
            locationConditionType = 0,
            location = location,
            alarmDate = "",
            alarmId = alarmIdStr,
            isEnabled = isEnabled,
            ringOn = isEnabled,
    )
}
