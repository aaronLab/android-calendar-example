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
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCalendar()
    }

    private fun setupCalendar() {
        // Example Days (Today -1 ~ Today -9) Set Up
        val myDays = mutableListOf<LocalDate>()
        var i: Long = 1
        while (i < 10) {
            myDays.add(LocalDate.now().minusDays(i))
            i ++
        }

        Log.e("myDays", "$myDays")

        // [a view holder for each date cell = entire calendar]
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
                if (day.date in myDays) {
                    container.textView.setTextColor(Color.WHITE)
                    container.view.setBackgroundColor(Color.RED)
                } else {
                    container.textView.setTextColor(Color.BLACK)
                    container.view.setBackgroundColor(Color.TRANSPARENT)
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