package com.sample.getcontacts.view

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.getcontacts.R
import com.sample.getcontacts.databinding.ItemContactBinding
import com.sample.getcontacts.model.Contact
import com.sample.getcontacts.model.Contact.Companion.loadImage
import java.util.*

class ContactListAdapter(
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private var contacts = mutableListOf<Contact>()

    class ContactViewHolder(itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        var binding = itemContactBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_contact,
                parent,
                false
            )
        )
    }

    fun setContacts(updatedContacts : List<Contact>){
        this.contacts = updatedContacts.toMutableList()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.binding.contact = contacts.get(position)

        if (contacts.get(position).photoUri != null) {
            holder.binding.drawableTextView.visibility = View.GONE
            holder.binding.contactPhoto.visibility = View.VISIBLE
            loadImage(holder.binding.contactPhoto, contacts.get(position).photoUri)
        } else {
            holder.binding.contactPhoto.visibility = View.GONE
            holder.binding.drawableTextView.visibility = View.VISIBLE
            val gradientDrawable = holder.binding.drawableTextView.background as GradientDrawable
            gradientDrawable.setColor(randomColor)
            val serviceSubString = contacts.get(position).name.substring(0, 2)
            holder.binding.drawableTextView.text = serviceSubString.uppercase(Locale.getDefault())
        }

        holder.binding.contactCheckBox.setOnClickListener {
            if (holder.binding.contactCheckBox.isChecked) {
                contacts.get(position).isChecked = true
                onItemClickListener.onItemClicked(true, contacts.get(position))
            } else {
                contacts.get(position).isChecked = false
                onItemClickListener.onItemClicked(false, contacts.get(position))
            }
        }
    }

    /**
     * @return a random color which is used a background by
     * services initial letters
     */
    private val randomColor: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    override fun getItemCount(): Int {
        return contacts.size
    }
}