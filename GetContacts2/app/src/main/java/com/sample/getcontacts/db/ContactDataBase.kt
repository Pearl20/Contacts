package com.sample.getcontacts.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sample.getcontacts.model.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDataBase() : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDataBase? = null

        fun getInstance(context: Context): ContactDataBase? {
            if (INSTANCE == null) {
                synchronized(ContactDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDataBase::class.java, "contact.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}