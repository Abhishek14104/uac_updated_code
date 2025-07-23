import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:ultimate_alarm_clock/app/modules/debug/controllers/debug_controller.dart';

class DataTransferLogsView extends StatelessWidget {
  final DebugController controller = Get.find<DebugController>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Data Transfer Logs'),
      ),
      body: Obx(() {
        final receivedAlarms = controller.receivedAlarms;

        if (receivedAlarms.isEmpty) {
          return const Center(
            child: Text(
              'No alarms received yet.',
              style: TextStyle(color: Colors.black),
            ),
          );
        }

        return ListView.builder(
          padding: const EdgeInsets.all(12),
          itemCount: receivedAlarms.length,
          itemBuilder: (context, index) {
            final alarm = receivedAlarms[index];
            return GestureDetector(
              onTap: () => showDialog(
                context: context,
                builder: (_) => AlertDialog(
                  title: const Text('Alarm Details', style: TextStyle(color: Colors.black)),
                  content: SingleChildScrollView(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        _detailText('Time', alarm.alarmTime),
                        _detailText('ID', alarm.alarmID),
                        _detailText('Enabled', alarm.isEnabled),
                        _detailText('One-time', alarm.isOneTime),
                        _detailText('Days', alarm.days),
                        _detailText('Location Enabled', alarm.isLocationEnabled),
                        _detailText('Location', alarm.location),
                        _detailText('Ringtone', alarm.ringtoneName),
                        _detailText('Label', alarm.label),
                        _detailText('Owner', '${alarm.ownerName} (${alarm.ownerId})'),
                        _detailText('Guardian', '${alarm.isGuardian} (${alarm.guardian})'),
                        _detailText('Call', alarm.isCall),
                        _detailText('Interval', alarm.intervalToAlarm),
                        _detailText('RingOn', alarm.ringOn),
                        _detailText('Mutex Lock', alarm.mutexLock),
                        _detailText('Alarm Date', alarm.alarmDate),
                      ],
                    ),
                  ),
                  actions: [
                    TextButton(
                      onPressed: () => Navigator.pop(context),
                      child: const Text('Close', style: TextStyle(color: Colors.black)),
                    ),
                  ],
                ),
              ),
              child: Card(
                color: Colors.white.withOpacity(0.9),
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                margin: const EdgeInsets.only(bottom: 10),
                child: Padding(
                  padding: const EdgeInsets.all(14),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text('Time: ${alarm.alarmTime}',
                          style: const TextStyle(fontSize: 16, color: Colors.black)),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: [
                          Text('ID: ${alarm.alarmID}',
                              style: const TextStyle(fontSize: 14, color: Colors.black)),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            );
          },
        );
      }),
    );
  }

  Widget _detailText(String label, dynamic value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2.0),
      child: Text('$label: $value', style: const TextStyle(color: Colors.black)),
    );
  }
}