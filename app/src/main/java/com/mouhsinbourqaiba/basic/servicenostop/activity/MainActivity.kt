package com.mouhsinbourqaiba.basic.servicenostop.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mouhsinbourqaiba.basic.servicenostop.R
import com.mouhsinbourqaiba.basic.servicenostop.action.Actions
import com.mouhsinbourqaiba.basic.servicenostop.service.EndlessService
import com.mouhsinbourqaiba.basic.servicenostop.service.ServiceState
import com.mouhsinbourqaiba.basic.servicenostop.service.getServiceState
import com.mouhsinbourqaiba.basic.servicenostop.util.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.app_name)

        findViewById<Button>(R.id.btnStartService).let {
            it.setOnClickListener {
                log(" SERVICE  START ON DEMAND")
                actionOnService(Actions.START)
            }
        }

        findViewById<Button>(R.id.btnStopService).let {
            it.setOnClickListener {
                log("SERVICE STOP ON DEMAND")
                actionOnService(Actions.STOP)
            }
        }
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            log("Starting the service in < 26 Mode")
            startService(it)
        }
    }
}

