package com.rudnitski.microlife.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.utils.Size
import com.rudnitski.microlife.databinding.FragmentDashboardBinding
import com.rudnitski.microlife.viewmodels.DashboardViewModel
import com.rudnitski.microlife.viewmodels.HabitJournalViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var calendarView: CalendarView
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val habitJournalViewModel: HabitJournalViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val adapter = HabitJournalAdapter()
        binding.habitJournalList.adapter = adapter

        habitJournalViewModel.habitList.observe(requireActivity(), {
            it.let {
                adapter.submitList(it)
            }
        })

        val root: View = binding.root
        binding.calendarView.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize =  Size(dayWidth, dayHeight)
        }
        calendarView = binding.calendarView
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day, calendarView)
            override fun create(view: View)= DayViewContainer(view, dashboardViewModel.selected, requireActivity())
        }

        val currentMonth = YearMonth.now()
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        calendarView.setup(currentMonth, currentMonth.plusMonths(3), firstDayOfWeek)
        calendarView.scrollToDate(LocalDate.now())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}