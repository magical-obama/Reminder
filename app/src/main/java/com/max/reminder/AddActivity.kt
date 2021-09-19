package com.max.reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddActivity : AppCompatActivity() {

    var entries: ArrayList<Reminder> = ArrayList<Reminder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val stringEntries = extras.getString("entries")
                if (stringEntries != null) {
                    entries = Json.decodeFromString<ArrayList<Reminder>>(stringEntries)
                }
            }
        }

        findViewById<ExtendedFloatingActionButton>(R.id.buttonAdd).setOnClickListener {
            val title = findViewById<TextInputLayout>(R.id.inputTitle).editText?.text.toString()
            Log.d("AddActivity","Title: " + title)
            val description = findViewById<TextInputLayout>(R.id.inputDescription).editText?.text.toString()
            Log.d("AddActivity", "Desc: " + description)
            val date = findViewById<DatePicker>(R.id.inputDate)
            val time = findViewById<TimePicker>(R.id.inputTime)

            val combinedDate = date.year.toString() + date.month.toString().padStart(2, '0') + date.dayOfMonth.toString().padStart(2,'0')
            val combinedTime = time.hour.toString().padStart(2, '0') + ":" + time.minute.toString().padStart(2,'0') + ":" + "00"

            entries.add(Reminder(title, description, LocalDate.parse(combinedDate, DateTimeFormatter.BASIC_ISO_DATE).toString(), LocalTime.parse(combinedTime, DateTimeFormatter.ISO_LOCAL_TIME).toString()))

            val intentString: String = Json.encodeToString(entries)

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("entries", intentString)
            }
            startActivity(intent)
        }

        findViewById<ExtendedFloatingActionButton>(R.id.buttonCancel).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}