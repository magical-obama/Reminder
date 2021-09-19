package com.max.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReminderAdapter(private val dataSet: Array<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewDate: TextView
        val textViewTime: TextView

        init {
            textViewTitle = view.findViewById(R.id.textViewTitle)
            textViewDescription = view.findViewById(R.id.textViewDescription)
            textViewDate = view.findViewById(R.id.textViewDate)
            textViewTime = view.findViewById(R.id.textViewTime)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.reminder_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textViewTitle.text = dataSet[position].title
        viewHolder.textViewDescription.text = dataSet[position].description.toString()
        viewHolder.textViewDate.text = dataSet[position].dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        viewHolder.textViewTime.text = dataSet[position].dueTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    override fun getItemCount() = dataSet.size

}