package com.max.reminder

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAmount
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
class Reminder(val title: String,
               val description: String?,
               val dueDate: String,
               val dueTime: String)