package com.tromian.test.testcontacts.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tromian.test.testcontacts.R
import com.tromian.test.testcontacts.domain.Contact

class ContactListAdapter(
    val itemClickCallback: (itemId: Int) -> Unit
) : ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.phone == newItem.phone

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem == newItem
        }
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivPreview)
        val name: TextView = view.findViewById(R.id.tvPreview)

        @SuppressLint("SetTextI18n")
        fun bind(contact: Contact) {
            name.text = "${contact.firstName} ${contact.lastName}"
            Glide.with(itemView).load(contact.pictureUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactListAdapter.ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(inflater.inflate(R.layout.contact_preview_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ContactListAdapter.ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickCallback(position)
        }
    }


}