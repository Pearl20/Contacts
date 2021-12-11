package com.sample.getcontacts.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sample.getcontacts.model.Contact
import com.sample.getcontacts.model.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(mApplication: Application) : AndroidViewModel(mApplication) {
    private val contacts: ObservableArrayList<Contact> = ObservableArrayList()
    private lateinit var contactsFromDB: List<Contact>
    private val repository: ContactRepository =
        ContactRepository(getApplication<Application>().applicationContext)

    fun getContacts(): List<Contact> {
        contacts.addAll(repository.fetchContacts())
        viewModelScope.launch {
            try {
                contactsFromDB = repository.getContact()
                if (::contactsFromDB.isInitialized) {
                    for (contact: Contact in contactsFromDB) {
                        contacts.find { it.name.equals(contact.name) }?.isChecked =
                            contact.isChecked
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return contacts
    }

    fun insertContactToDB(contact: Contact) {
        viewModelScope.launch {
            try {
                repository.insertContact(contact)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteContactToDB(contact: Contact) {
        viewModelScope.launch {
            try {
                repository.deleteContact(contact)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}