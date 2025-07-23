package com.ccextractor.ultimate_alarm_clock.communication

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.*
import java.util.*

object MessageSender {
    private const val TAG = "UAC_MessageSender"

    fun sendAction(context: Context, action: String) {
        Log.d(TAG, "Attempting to send action: $action")
        fallbackToDataLayer(context, action)
    }

    private fun fallbackToDataLayer(context: Context, action: String) {
        val timestamp = System.currentTimeMillis()
        val path = "/uac_fallback/$timestamp"
    
        val request = PutDataMapRequest.create(path).apply {
            dataMap.putString("action", action)
            dataMap.putLong("timestamp", timestamp)
        }.asPutDataRequest().setUrgent()
    
        Log.d(TAG, "Sending fallback data via DataClient to path: $path")
    
        Wearable.getDataClient(context).putDataItem(request)
            .addOnSuccessListener {
                Log.d(TAG, "Fallback sent via DataClient: $action")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Fallback DataClient failed", e)
            }
    }    
}