package com.app.ecosort.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ecosort.databinding.ItemHistoryBinding
import com.app.ecosort.view.history.HistoryActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.database.History

class HistoryAdapter(private val listHistory: List<History>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(history.image)
                    .into(ivHistory)
                tvHistory.text = history.description
                cvHistory.setOnClickListener {
                    val intent = Intent(it.context, HistoryActivity::class.java)
//                    intent.putExtra(HistoryActivity.EXTRA_HISTORY, history)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}