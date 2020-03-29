package com.example.typeracer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.repo.model.Race

class MyRaceHistoryAdapter(var data: List<Race>? = null) :
    RecyclerView.Adapter<MyRaceHistoryAdapter.MyRaceHistoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRaceHistoryVH {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_race_history_row, parent, false)
        return MyRaceHistoryVH(rootView)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: MyRaceHistoryVH, position: Int) {
        data?.let { holder.bind(it[position]) }
    }

    inner class MyRaceHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)

        internal fun bind(item: Race) {
            binding?.setVariable(BR.item, item)
            binding?.executePendingBindings()
        }
    }
}