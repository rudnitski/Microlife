package com.rudnitski.microlife.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rudnitski.microlife.databinding.ListItemHabitJournalBinding
import com.rudnitski.microlife.db.HabitAndJournal
import com.rudnitski.microlife.db.HabitsJournal
import com.rudnitski.microlife.viewmodels.HabitAndJournalViewModel

class HabitJournalAdapter: ListAdapter<HabitAndJournal, HabitJournalAdapter.HabitJournalViewHolder>(HabitJournalDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitJournalViewHolder {
        return HabitJournalViewHolder(ListItemHabitJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HabitJournalViewHolder, position: Int) {
        val habitJournal = getItem(position)
        holder.bind(habitJournal)
    }

    class HabitJournalViewHolder(private val binding: ListItemHabitJournalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HabitAndJournal) {
            with (binding){
                viewModel = HabitAndJournalViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object HabitJournalDiffUtil : DiffUtil.ItemCallback<HabitAndJournal>() {
        override fun areItemsTheSame(oldItem: HabitAndJournal, newItem: HabitAndJournal): Boolean {
            return oldItem.habitsJournal[0].id == newItem.habitsJournal[0].id
        }

        override fun areContentsTheSame(oldItem: HabitAndJournal, newItem: HabitAndJournal): Boolean {
            return oldItem.habit.id == newItem.habit.id &&
                    oldItem.habitsJournal[0].habitUseDate == newItem.habitsJournal[0].habitUseDate &&
                    oldItem.habitsJournal[0].microlifes == newItem.habitsJournal[0].microlifes &&
                    oldItem.habitsJournal[0].userId == newItem.habitsJournal[0].userId
        }
    }
}