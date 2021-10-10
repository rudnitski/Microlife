package com.rudnitski.microlife.ui.dashboard

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import com.rudnitski.microlife.R
import com.rudnitski.microlife.databinding.CalendarDayLayoutBinding
import java.lang.ClassCastException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayViewContainer(
    view: View,
    private val liveData: MutableLiveData<LocalDate>,
    private val lifecycleOwner: LifecycleOwner
    ) : ViewContainer(view) {

    val bind = CalendarDayLayoutBinding.bind(view)
    lateinit var day: CalendarDay
    lateinit var calendarView: CalendarView
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")
    private lateinit var selectedDate: LocalDate

/*     private val activity: FragmentActivity by lazy {
        try {
            view.context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }*/
//    private val dashboardViewModel: DashboardViewModel by activity.viewModels()

    init {
        liveData.observe(lifecycleOwner, Observer {
            selectedDate = it
        })
        view.setOnClickListener{
            val firstDay = calendarView.findFirstVisibleDay()
            val lastDay = calendarView.findLastVisibleDay()
            if (firstDay == day) {
                calendarView.smoothScrollToDate(day.date)
            } else if (lastDay == day) {
                calendarView.smoothScrollToDate(day.date.minusDays(4))
            }

            if (selectedDate != day.date) {
                val oldDate = selectedDate;
                selectedDate = day.date;
                liveData.value = day.date
                calendarView.notifyDateChanged(day.date)
                oldDate.let { calendarView.notifyDateChanged(it) }
            }
        }
    }

    fun bind(day: CalendarDay, calendarView: CalendarView) {
        this.day = day
        this.calendarView = calendarView
        bind.exSevenDateText.text = dateFormatter.format(day.date)
        bind.exSevenDayText.text = dayFormatter.format(day.date)
        bind.exSevenMonthText.text = monthFormatter.format(day.date)

        bind.exSevenDateText.setTextColor(ContextCompat.getColor(view.context, if (day.date == selectedDate) R.color.example_7_yellow else R.color.example_7_white))
        bind.exSevenSelectedView.isVisible = day.date == selectedDate
    }
}