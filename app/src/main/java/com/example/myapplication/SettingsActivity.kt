package com.example.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {
    val languages = arrayOf("English","Polish")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val languageSpinner = findViewById<Spinner>(R.id.languages)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, languages)
        languageSpinner.adapter = arrayAdapter
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // zrobić zmianę języka
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val addSensorButton = findViewById<Button>(R.id.add_sensor)
        addSensorButton.setOnClickListener {
            Snackbar.make(
                findViewById(R.id.setting_layout),
                getString(R.string.ble_sensor_add_error),
                Snackbar.LENGTH_SHORT
            ).show()

        }
        val changeThemeButton = findViewById<Button>(R.id.change_theme)
        changeThemeButton.setOnClickListener { chooseThemeDialog() }
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change app theme")
        val styles = arrayOf(getString(R.string.light), getString(R.string.dark), getString(R.string.sys_default))
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }

        }

        val dialog = builder.create()
        dialog.show()
    }
}