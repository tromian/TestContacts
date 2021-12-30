package com.tromian.test.testcontacts.data.db

import androidx.room.*

@Dao
interface ContactsDAO {

    @Query("select count(*) from contacts")
    fun getContactsCount() : Int

    @Query("delete from contacts")
    fun clearContactTable()

    @Query("select * from contacts")
    fun getContactList() : List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContactList(list: List<ContactEntity>)

    @Delete
    fun deleteContact(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateContact(contactEntity: ContactEntity)
}