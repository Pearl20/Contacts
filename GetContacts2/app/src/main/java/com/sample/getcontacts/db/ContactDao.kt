package com.sample.getcontacts.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sample.getcontacts.model.Contact

@Dao
interface ContactDao {

    @Insert
    fun insertContact(contact: Contact)

    @Query("SELECT * FROM Contact")
    fun getContact(): List<Contact>

    @Delete
    fun deleteContact(contact: Contact)
}