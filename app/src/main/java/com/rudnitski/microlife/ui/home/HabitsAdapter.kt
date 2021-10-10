package com.rudnitski.microlife.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rudnitski.microlife.R
import com.rudnitski.microlife.db.Habit

class HabitsAdapter(
    private val onClickListener: OnClickListener
    ) : ListAdapter<Habit, HabitsAdapter.HabitViewHolder>(HabitDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder.create(parent);
    }

    override fun onBindViewHolder(holderHabit: HabitViewHolder, position: Int) {
        val data = getItem(position)
        holderHabit.itemView.setOnClickListener {
            onClickListener.onCLick(data)
        }
        holderHabit.bind(data)
    }

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val resources = itemView.resources;
        private val packageName = itemView.context.packageName

        fun bind(habit: Habit) {
            val resId = resources.getIdentifier(habit.resourceUrl, "drawable", packageName)
            image.setImageResource(resId);
            title.text = habit.title
        }

        companion object {
            fun create(parent: ViewGroup): HabitViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
                return HabitViewHolder(view);
            }
        }
    }

    companion object HabitDiffUtil : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.men_microlives_per_day == newItem.men_microlives_per_day
                    && oldItem.women_microlives_per_day == newItem.women_microlives_per_day
                    && oldItem.resourceUrl == newItem.resourceUrl
        }
    }

    class OnClickListener(val clickListener: (habit: Habit) -> Unit) {
        fun onCLick(habit: Habit) = clickListener(habit)
    }
}