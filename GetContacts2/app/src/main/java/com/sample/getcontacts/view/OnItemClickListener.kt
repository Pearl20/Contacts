package com.sample.getcontacts.view

import com.sample.getcontacts.model.Contact

interface OnItemClickListener {
    fun onItemClicked(isChecked: Boolean,contact: Contact)
}