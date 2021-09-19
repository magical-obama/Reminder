package com.max.reminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.encodeToString
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    var entries: ArrayList<Reminder> = ArrayList<Reminder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                Toast.makeText(this,"No Extras", Toast.LENGTH_SHORT).show()
            } else {
                val stringEntries = extras.getString("entries")
                if (stringEntries != null) {
                    entries = Json.decodeFromString<ArrayList<Reminder>>(stringEntries)
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intentString: String = Json.encodeToString(entries)
//            for (item in entries) {
//                intentArray.add(Json.encodeToString(item))
//            }

            val intent = Intent(this, AddActivity::class.java).apply {
                putExtra("entries", intentString)
            }
            startActivity(intent)
        }
        // entries.add(Reminder("English HW", "Write Essay", LocalDate.of(2021, 10,1).toString(), LocalTime.of(10, 0).toString()))
        // entries.add(Reminder("Gardening", "Plant Roses", LocalDate.of(2021, 11, 7).toString(), LocalTime.of(12, 45).toString()))

        // val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10")

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ReminderAdapter(entries.toTypedArray())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            Toast.makeText(this, "Open Settings", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }



}