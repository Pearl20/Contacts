package com.sample.getcontacts.view

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.sample.getcontacts.R
import com.sample.getcontacts.model.Contact
import com.sample.getcontacts.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnRequestPermissionsResultCallback, OnItemClickListener {
    private val CONTACTS_READ_REQ_CODE = 100
    lateinit var viewModel: ContactViewModel
    private val adapter = ContactListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        if (ContextCompat.checkSelfPermission(
                this,
                READ_CONTACTS
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            adapter.setContacts(viewModel.getContacts())
        } else {
            requestPermissions(
                this,
                arrayOf(READ_CONTACTS), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACTS_READ_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapter.setContacts(viewModel.getContacts())
        }

    }

    override fun onItemClicked(isChecked: Boolean, contact: Contact) {
        Log.e("Clicked", contact.isChecked.toString())
        if (isChecked)
            viewModel.insertContactToDB(contact)
        else
            viewModel.deleteContactToDB(contact)

    }
}