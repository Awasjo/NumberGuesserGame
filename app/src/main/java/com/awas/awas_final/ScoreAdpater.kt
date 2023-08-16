package com.awas.awas_final

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awas.awas_final.database.Score
import com.awas.awas_final.databinding.ActivityRvItemBinding

class ScoreAdpater(private val values: List<Score>) : RecyclerView.Adapter<ScoreAdpater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ActivityRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentScore = values[position]

        holder.tvName.text = currentScore.name
        holder.tvScore.text = currentScore.win.toString()

        Log.d("ScoreAdapter", "onBindViewHolder: $currentScore")
    }

    override fun getItemCount(): Int = values.size


    inner class ViewHolder(binding: ActivityRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvName: TextView = binding.tvName
        val tvScore: TextView = binding.tvScore
    }
}