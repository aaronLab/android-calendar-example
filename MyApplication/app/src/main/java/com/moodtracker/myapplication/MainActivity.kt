package com.moodtracker.myapplication

import android.graphics.Color
import android.icu.util.LocaleData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAmount
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCalendar()
    }

    private fun setupCalendar() {
        // [Example Events Type]
        val events: HashMap<LocalDate, List<HashMap<String, String>>> = hashMapOf()
        // [Put Dates for Example]
        // Today, Emoji, Color
        events[LocalDate.now()] = listOf(hashMapOf("emoji" to "💪"), hashMapOf("color" to "red"))
        // Today -5, Emoji, Color
        events[LocalDate.now().minusDays(5)] = listOf(hashMapOf("emoji" to "🤔"), hashMapOf("color" to "blue"))
        // [Test Logs]
        Log.e("events", "$events")
        for (event in events) {
            Log.e("event", "Date: ${event.key}")
            Log.e("event", "Value Array ${event.value}")
            Log.e("event", "Emoji Value ${event.value[0]["emoji"]}")
            Log.e("event", "Color Value ${event.value[1]["color"]}")
        }

        // [a view holder for each date cell = entire calendar]
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()

                for (event in events) {
                    if (day.date == event.key) {
                        if (event.value[0]["emoji"] != "") {
                            container.subtitle.text = event.value[0]["emoji"]
                        }
                        if (event.value[1]["color"] == "red") {
                            container.textView.setTextColor(Color.WHITE)
                            container.textView.setBackgroundColor(Color.RED)
                        } else {
                            if (event.value[1]["color"] == "blue") {
                                container.textView.setTextColor(Color.WHITE)
                                container.textView.setBackgroundColor(Color.BLUE)
                            } else {
                                container.textView.setTextColor(Color.BLACK)
                                container.view.setBackgroundColor(Color.TRANSPARENT)
                            }
                        }
                    }
                }
            }
        }

        // [Setup Calendar]
        val currentMonth = YearMonth.now()
        val firstMonth = YearMonth.of(1900, 1)
        val lastMonth = currentMonth.plusMonths(12)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

    }
}