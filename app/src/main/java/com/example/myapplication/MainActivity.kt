package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    val carsList =
        arrayOf("Mazda MX5", "BMW E36", "Opel Astera 2009", "Renault Fluence 2", "Porshe 911")
    val carIdList = arrayOf(
        R.drawable.mx5,
        R.drawable.bmwe36,
        R.drawable.astra2009,
        R.drawable.fluence,
        R.drawable.porshe911
    )

    var currentCarId = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CHANNEL_ID = "i.apps.notifications"
        val mChannel = NotificationChannel(CHANNEL_ID, "Channel", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Powiadomienie")
            .setContentText("Tekst powiadomienia")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

        val carSpinner = findViewById<Spinner>(R.id.car_list)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, carsList)
        carSpinner.adapter = arrayAdapter
        carSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentCarId = position
                val image = findViewById<ImageView>(R.id.car_image)
                Picasso.get()
                    .load("https://www.autocentrum.pl/ac-file/car-version/5c50468457502a630958badd/mazda-mx-5-iv.jpg")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(carIdList[currentCarId])
                    .resize(200, 200)         //optional
                    .centerCrop()            //optional
                    .into(image);
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }

        val image = findViewById<ImageView>(R.id.car_image)
        image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.youtube.com/**YOURCHANNEL**")
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }
        val sessionButton = findViewById<Button>(R.id.new_session)
        sessionButton.setOnClickListener {
            val intent = Intent(this, SessionActivity::class.java)
            intent.putExtra("CAR_NAME", carsList[currentCarId])
            startActivity(intent)
        }
        val overviewButton = findViewById<Button>(R.id.session_overwiew)
        overviewButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        val accountButton = findViewById<Button>(R.id.account)
        accountButton.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        val nextButton = findViewById<Button>(R.id.next_car)
        nextButton.setOnClickListener {
            currentCarId = (currentCarId + 1) % carIdList.size
            carSpinner.setSelection(currentCarId)
        }

        val prevButton = findViewById<Button>(R.id.previous_car)

        prevButton.setOnClickListener {
            currentCarId = if (currentCarId == 0) {
                carIdList.size - 1
            } else {
                (currentCarId - 1) % carIdList.size
            }

            carSpinner.setSelection(currentCarId)
        }
    }
}