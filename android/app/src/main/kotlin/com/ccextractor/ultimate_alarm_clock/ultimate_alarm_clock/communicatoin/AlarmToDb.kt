package com.ccextractor.ultimate_alarm_clock.communication

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ccextractor.ultimate_alarm_clock.AlarmModel
import com.google.gson.Gson
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

object AlarmModelSender {

    private const val CHANNEL = "com.ccextractor.alarm_channel"
    private const val TAG = "UAC_AlarmModelSender"

    fun sendToFlutter(flutterEngine: FlutterEngine, alarm: AlarmModel) {
        val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
    
        val alarmMap = mapOf(
            "alarmTime" to alarm.alarmTime,
            "alarmID" to alarm.alarmId,
            "ownerId" to "watch",
            "ownerName" to "Watch",
            "lastEditedUserId" to "watch",
            "mutexLock" to false,
            // "days" to alarm.days.map { it == '1' }, // List<bool>
            "days" to if (alarm.days.isEmpty()) List(7) { false } else alarm.days.map { it == '1' },
            "intervalToAlarm" to 0,
            "isActivityEnabled" to false,
            "minutesSinceMidnight" to alarm.minutesSinceMidnight,
            "isLocationEnabled" to (alarm.isLocationEnabled == 1),
            "locationConditionType" to alarm.locationConditionType,
            "isSharedAlarmEnabled" to false,
            "isWeatherEnabled" to (alarm.isWeatherEnabled == 1),
            "weatherConditionType" to alarm.weatherConditionType,
            "activityConditionType" to alarm.activityConditionType,
            "location" to alarm.location,
            "weatherTypes" to listOf<Int>(), // Send as actual list
            "isMathsEnabled" to false,
            "mathsDifficulty" to 0,
            "numMathsQuestions" to 0,
            "isShakeEnabled" to false,
            "shakeTimes" to 0,
            "isQrEnabled" to false,
            "qrValue" to "",
            "isPedometerEnabled" to false,
            "numberOfSteps" to 0,
            "activityInterval" to alarm.activityInterval,
            "mainAlarmTime" to alarm.alarmTime,
            "label" to "",
            "isOneTime" to (alarm.isOneTime == 1),
            "snoozeDuration" to 5,
            "maxSnoozeCount" to 3,
            "gradient" to 0,
            "ringtoneName" to "Default",
            "note" to "",
            "deleteAfterGoesOff" to false,
            "showMotivationalQuote" to false,
            "volMax" to 1.0,
            "volMin" to 0.5,
            "activityMonitor" to alarm.activityMonitor,
            "alarmDate" to alarm.alarmDate,
            "profile" to "Default",
            "isGuardian" to false,
            "guardianTimer" to 0,
            "guardian" to "",
            "isCall" to false,
            "isSunriseEnabled" to false,
            "sunriseDuration" to 0,
            "sunriseIntensity" to 0.5,
            "sunriseColorScheme" to 0,
            "sharedUserIds" to listOf<String>(), // Send as actual list
            "ringOn" to (alarm.ringOn == 1),
            "isEnabled" to (alarm.isEnabled == 1), 
        )
    
        val json = Gson().toJson(alarmMap)
        Log.d(TAG, "📤 Sending alarm to Flutter: $json")
    
        Handler(Looper.getMainLooper()).post {
            methodChannel.invokeMethod("onWatchAlarmReceived", json)
        }
    }    
}