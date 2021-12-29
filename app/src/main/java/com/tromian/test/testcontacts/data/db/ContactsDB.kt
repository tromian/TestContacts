package com.tromian.test.testcontacts.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        ContactEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ContactsDB : RoomDatabase() {

    abstract fun contactsDao(): ContactsDAO

    companion object {
        private const val DATABASE_NAME = "contacts_test.db"

        private var INSTANCE: ContactsDB? = null

        private val lock = Any()

        fun getInstance(appContext: Context): ContactsDB {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        appContext,
                        ContactsDB::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }

    }
}