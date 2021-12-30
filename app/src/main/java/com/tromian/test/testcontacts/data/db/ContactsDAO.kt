package com.tromian.test.testcontacts.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDAO {

    @Query("select count(*) from contacts")
    fun getContactsCount() : Int

    @Query("delete from contacts")
    fun clearContactTable()

    @Query("select * from contacts order by firstName asc")
    fun getContactList(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContactList(list: List<ContactEntity>)

    @Delete
    fun deleteContact(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateContact(contactEntity: ContactEntity)
}