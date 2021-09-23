package com.max.reminder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var entries: ArrayList<Reminder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupSharedPreferences()

        val darkModeEnabled = getSharedPreferences("com.max.reminder", Context.MODE_PRIVATE).getBoolean("dark_mode", false)
        if (darkModeEnabled) {
            setDarkMode(true)
        } else {
            setDarkMode(false)
        }

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                Toast.makeText(this, "No Extras", Toast.LENGTH_SHORT).show()
            } else {
                val stringEntries = extras.getString("entries")
                if (stringEntries != null) {
                    entries = Json.decodeFromString(stringEntries)
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

    private fun setupSharedPreferences() {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setDarkMode(value: Boolean) {
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals("dark_mode")) {
            if (sharedPreferences != null) {
                if (sharedPreferences.getBoolean("dark_mode", false)) {
                    sharedPreferences.edit().putBoolean("dark_mode", sharedPreferences.getBoolean("dark_mode", false)).apply()
                    setDarkMode(true)
                } else {
                    sharedPreferences.edit().putBoolean("dark_mode", sharedPreferences.getBoolean("dark_mode", false)).apply()
                    setDarkMode(false)
                }
            }
        }
    }




}