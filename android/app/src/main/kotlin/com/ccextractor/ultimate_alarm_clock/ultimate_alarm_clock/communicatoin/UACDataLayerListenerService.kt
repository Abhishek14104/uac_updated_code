package com.ccextractor.ultimate_alarm_clock.communication

import android.util.Log
import com.ccextractor.ultimate_alarm_clock.communication.MinimalAlarmDTO
import com.ccextractor.ultimate_alarm_clock.communication.toAlarmModel
import com.google.android.gms.wearable.*
import com.google.gson.Gson
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class UACDataLayerListenerService : WearableListenerService() {
    private val TAG = "UAC_DataLayerService"

    companion object {
        // Inject or initialize this at app startup or from MainActivity
        var flutterEngine: FlutterEngine? = null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "🟢 Service created and alive")
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        Log.d(TAG, "📡 DataLayerService triggered")

        for (event in dataEvents) {
            val item = event.dataItem
            val path = item.uri.path ?: continue
            val dataMap = DataMapItem.fromDataItem(item).dataMap

            Log.d(TAG, "➡ $path | data: $dataMap")

            when {
                path == "/uac_alarm_sync/alarm" -> {
                    val json = dataMap.getString("alarm_json")
                    val dto = Gson().fromJson(json, MinimalAlarmDTO::class.java)
                    val fullAlarm = dto.toAlarmModel()
                    Log.d(TAG, "✅ Background Alarm Received: $fullAlarm")

                    flutterEngine?.let {
                        AlarmModelSender.sendToFlutter(it, fullAlarm)
                    } ?: Log.w(TAG, "⚠️ flutterEngine not available. Cannot send to Flutter.")
                }

                path == "/uac_alarm_sync/action" -> {
                    val action = dataMap.getString("alarm_json")
                    Log.d(TAG, "✅ Background Action: $action")
                }

                path.startsWith("/uac_fallback/") -> {
                    val action = dataMap.getString("action")
                    val timestamp = dataMap.getLong("timestamp")
                    Log.d(TAG, "✅ Fallback received: action=$action at $timestamp")
                }
            }
        }
    }
}
