package com.mindease.kipsigis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(
    private val contacts: List<Contact>,
    private val onDelete: (Int) -> Unit,
    private val onCall: (String) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val phone: TextView = view.findViewById(R.id.tvPhone)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val btnCall: ImageButton = view.findViewById(R.id.btnCall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]

        holder.name.text = contact.name
        holder.phone.text = contact.phone

        holder.btnDelete.setOnClickListener { onDelete(contact.id) }
        holder.btnCall.setOnClickListener { onCall(contact.phone) }
    }
}
