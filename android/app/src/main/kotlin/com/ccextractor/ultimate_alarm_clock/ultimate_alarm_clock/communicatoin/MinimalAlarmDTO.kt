package com.ccextractor.ultimate_alarm_clock.communication

//! data object that is being communicated on both sides.
data class MinimalAlarmDTO(
    val id: Int,
    val time: String,
    val days: List<Int>,
    val isOneTime: Int,
    val isLocationEnabled: Boolean,
    val location: String,
    val isGuardian: Boolean,
    val guardian: String,
    val guardianTimer: Int,
    val isCall: Boolean,
    val fromWatch: Boolean,
    val isEnabled: Int,
)