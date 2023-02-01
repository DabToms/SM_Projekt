package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class SessionActivity : AppCompatActivity() , SensorEventListener {
    private var KEY_CURRENT_TIME: String = "currentTime"
    private var KEY_CURRENT_STATE: String = "currentState"
    private var KEY_CURRENT_ELAPSED: String = "currentElapsed"
    private lateinit var sensorManager: SensorManager
    private lateinit var distanceText: TextView
    private lateinit var accIndicator: FloatingActionButton
   // private lateinit var fusedLocationClient: FusedLocationProviderClient

    val NOT_RUNNING = 0
    val RUNNING = 1
    val PAUSED = 2
    var currentState = 0

    var timeElapsed: Long = 0
    lateinit var simpleChronometer: Chronometer

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("TEST", "Wywołano onSaveInstanceState.")
        outState.putLong(KEY_CURRENT_TIME, simpleChronometer.base)
        outState.putInt(KEY_CURRENT_STATE, currentState)
        outState.putLong(KEY_CURRENT_ELAPSED, timeElapsed)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        val lux =
            round(sqrt(event.values[0].pow(2) + event.values[1].pow(2) + event.values[2].pow(2)) * 100) / 100
        // distanceText.text = lux.toString()
        accIndicator.apply {
            translationX = event.values[0] * -10
            translationY = event.values[1] * 10
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

       // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            Log.d("Test", "onCreate: There is an accelerometer :)")

            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)?.also { accelerometer ->
                sensorManager.registerListener(
                    this,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST,
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            }
        } else {
            Log.d("Test", "onCreate: There is no accelerometer :(")
        }


        var carName = findViewById<TextView>(R.id.car_name)
        carName.text = intent.getStringExtra("CAR_NAME")

        distanceText = findViewById<TextView>(R.id.distance)
        accIndicator = findViewById<FloatingActionButton>(R.id.acc_indicator)

        val controllButton = findViewById<Button>(R.id.start_session)
        var sessionStorage = SessionStorage.getInstance()
        simpleChronometer = findViewById<View>(R.id.timer) as Chronometer

        if (savedInstanceState != null) {
            simpleChronometer.base = savedInstanceState.getLong(KEY_CURRENT_TIME)
            timeElapsed = savedInstanceState.getLong(KEY_CURRENT_ELAPSED)
            currentState = savedInstanceState.getInt(KEY_CURRENT_STATE)
            if (currentState === NOT_RUNNING) {
                simpleChronometer.base = SystemClock.elapsedRealtime()
                simpleChronometer.start()
                controllButton.text = "Pause"
                currentState = RUNNING
            } else if (currentState == RUNNING) {
                controllButton.text = "Reasume"
                simpleChronometer.stop()
                timeElapsed = simpleChronometer.base - SystemClock.elapsedRealtime()
                currentState = PAUSED;
            } else if (currentState == PAUSED) {
                simpleChronometer.base = SystemClock.elapsedRealtime() + timeElapsed
                simpleChronometer.start()
                controllButton.text = "Pause"
                currentState = RUNNING;
            }
        }

        val stopButton = findViewById<Button>(R.id.stop_session)


        controllButton.setOnClickListener()
        {
            if (currentState === NOT_RUNNING) {
                simpleChronometer.base = SystemClock.elapsedRealtime()
                simpleChronometer.start()
                controllButton.text = "Pause"
                currentState = RUNNING
            } else if (currentState == RUNNING) {
                controllButton.text = "Reasume"
                simpleChronometer.stop()
                timeElapsed = simpleChronometer.base - SystemClock.elapsedRealtime()
                currentState = PAUSED;
            } else if (currentState == PAUSED) {
                simpleChronometer.base = SystemClock.elapsedRealtime() + timeElapsed
                simpleChronometer.start()
                controllButton.text = "Pause"
                currentState = RUNNING;
            }
        }

        stopButton.setOnClickListener()
        {
            if (currentState != NOT_RUNNING) {
                currentState = NOT_RUNNING;
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val current = LocalDateTime.now().format(formatter)
                sessionStorage.addSession(
                    Session(
                        simpleChronometer.drawingTime,
                        69,
                        "Session " + current.toString()
                    )
                )
                simpleChronometer.base = SystemClock.elapsedRealtime()
                simpleChronometer.stop()
                controllButton.text = "Start"
                Snackbar.make(
                    findViewById(R.id.activity_session),
                    "Session saved on: " + current.toString() + " Distance: " + 0,
                    Snackbar.LENGTH_SHORT
                ).show()
                timeElapsed = 0
            }

        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}