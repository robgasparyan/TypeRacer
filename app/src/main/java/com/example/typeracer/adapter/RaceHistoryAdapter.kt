package com.example.typeracer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.repo.model.UserRace


class RaceHistoryAdapter(var data: List<UserRace>? = null) :
    RecyclerView.Adapter<RaceHistoryAdapter.RaceHistoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceHistoryVH {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.race_history_row, parent, false)
        return RaceHistoryVH(rootView)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: RaceHistoryVH, position: Int) {
        data?.let { holder.bind(it[position]) }
    }

    inner class RaceHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)

        internal fun bind(item: UserRace) {
            binding?.setVariable(BR.item, item)
            binding?.executePendingBindings()
        }
    }
}