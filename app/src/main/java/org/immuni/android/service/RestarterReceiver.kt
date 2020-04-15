package org.immuni.android.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bendingspoons.pico.Pico
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.immuni.android.ImmuniApplication
import org.immuni.android.managers.BluetoothManager
import org.immuni.android.picoMetrics.ForegroundServiceRestartedByAlarmManager
import org.immuni.android.util.log
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestarterReceiver : BroadcastReceiver(), KoinComponent {

    private val btManager: BluetoothManager by inject()
    private val pico: Pico by inject()

    override fun onReceive(context: Context, intent: Intent) {

        log("Restarter event received, restarting workers if needed...")

        btManager.scheduleBLEWorker(context)
        DeleteUserDataWorker.scheduleWork(context)

        // re-schedule next alarm

        AlarmsManager.scheduleWorks(ImmuniApplication.appContext)

        GlobalScope.launch {
            pico.trackEvent(ForegroundServiceRestartedByAlarmManager().userAction)
        }
    }
}