import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:ultimate_alarm_clock/app/modules/debug/controllers/debug_controller.dart';
import 'dart:convert';
import 'app/data/models/alarm_model.dart';
import 'package:ultimate_alarm_clock/app/data/providers/isar_provider.dart';
import 'package:ultimate_alarm_clock/app/data/providers/isar_provider.dart' as isar;

class AlarmSyncHandler {
  static const MethodChannel _channel =
      MethodChannel("com.ccextractor.alarm_channel");

  static void initListener() {
    _channel.setMethodCallHandler((call) async {
      if (call.method == "onWatchAlarmReceived") {
        final jsonString = call.arguments as String;
        final alarmMap = jsonDecode(jsonString);

        try {
          final alarm = AlarmModel.fromMap(alarmMap);
          await isar.IsarDb.addAlarm(alarm);
          debugPrint('from communication.dart 😊😊😊😊😊😊 ${alarm.alarmTime.runtimeType}');
          // Inject into DebugController
          final DebugController debugController = Get.find<DebugController>();
          debugController.receivedAlarms.add(alarm);
        } catch (e, st) {
          print("Failed to parse alarm from map: $e\n$st");
        }
      }
    });
  }
}