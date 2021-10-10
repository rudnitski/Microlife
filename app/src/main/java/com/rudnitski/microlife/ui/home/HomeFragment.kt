package com.rudnitski.microlife.ui.home

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.rudnitski.microlife.R
import com.rudnitski.microlife.databinding.FragmentHomeBinding
import com.rudnitski.microlife.db.Gender
import com.rudnitski.microlife.db.Habit
import com.rudnitski.microlife.db.HabitsJournal
import com.rudnitski.microlife.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ghyeok.stickyswitch.widget.StickySwitch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var habitsAdapter: HabitsAdapter
    private lateinit var textLifetime: TextView
    private lateinit var anim: LottieAnimationView
    private lateinit var genderSwitch: StickySwitch
    private var userId: Int = 0
    private val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView;
        anim = binding.anim;
        textLifetime = binding.nested.currentLifetime
        genderSwitch = binding.nested.genderSwitch
        textLifetime.text = "Your current lifetime: " + retrieveLifeTime().toString() + " minutes"
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        habitsAdapter = HabitsAdapter(HabitsAdapter.OnClickListener { habit ->
            onDataClickListener(habit)
        })
        recyclerView.adapter = habitsAdapter;

        homeViewModel.allHabits.observe(requireActivity(), Observer {
            it?.let { habitsAdapter.submitList(it) }
        })

        homeViewModel.user.observe(requireActivity(), {
            it?.let {
                genderSwitch.setDirection(
                    when (it.gender) {
                        Gender.MALE -> StickySwitch.Direction.LEFT
                        Gender.FEMALE -> StickySwitch.Direction.RIGHT
                    }
                )
                userId = it.id
            }
        })
        genderSwitch.onSelectedChangeListener = object : StickySwitch.OnSelectedChangeListener {
            override fun onSelectedChange(direction: StickySwitch.Direction, text: String) {
                homeViewModel.setGenderToUser(
                    when (direction) {
                        StickySwitch.Direction.LEFT -> Gender.MALE
                        StickySwitch.Direction.RIGHT -> Gender.FEMALE
                    }
                )
            }
        }
        return root
    }

    private fun retrieveLifeTime(): Int {
        val sharedPref = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE) ?: return 0
        return sharedPref.getInt(getString(R.string.life_minutes), 0)
    }

    private fun onDataClickListener(data: Habit) {
        val lifeTime = retrieveLifeTime()
        val newLifetime = lifeTime + data.men_microlives_per_day
        val sharedPref = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE) ?: return
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        with(sharedPref.edit()) {
            putInt(getString(com.rudnitski.microlife.R.string.life_minutes), newLifetime)
            apply()
        }
        anim.visibility = View.VISIBLE;
        anim.playAnimation()
        anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                anim.visibility = View.GONE;
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented")
            }
        })
        textLifetime.text = "Your current lifetime: " + retrieveLifeTime().toString() + " minutes + " + simpleDateFormat.format(calendar.time)

        homeViewModel.addHabitToRepo(HabitsJournal(0, data.id, userId, calendar, data.men_microlives_per_day))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}