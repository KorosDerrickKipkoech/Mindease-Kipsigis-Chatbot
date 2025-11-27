package com.mindease.kipsigis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class DiaryAdapter(
    private var entries: MutableList<DiaryEntry>,
    private val db: DatabaseHelper
) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val item = entries[position]

        holder.tvContent.text = item.content
        holder.tvTimestamp.text = formatTime(item.timestamp)

        holder.btnDelete.setOnClickListener {
            db.deleteDiaryEntry(item.id)
            entries.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = entries.size

    fun updateData(newList: List<DiaryEntry>) {
        entries = newList.toMutableList()
        notifyDataSetChanged()
    }

    private fun formatTime(t: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date(t))
    }
}
