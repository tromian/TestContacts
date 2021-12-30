package com.tromian.test.testcontacts.data

import android.app.Application
import android.util.Log
import com.tromian.test.testcontacts.data.db.ContactsDB
import com.tromian.test.testcontacts.data.network.RandomUserApi
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.utils.NetworkConnection
import com.tromian.test.testcontacts.utils.toDomain
import com.tromian.test.testcontacts.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val db: ContactsDB,
    private val api: RandomUserApi,
    private val appContext: Application,
) : ContactRepository {

    private fun contactTableIsNotEmpty(): Boolean{
        return (db.contactsDao().getContactsCount()>0)
    }

    override fun saveContactsToDB(list: List<Contact>) {
        db.contactsDao().clearContactTable()
        db.contactsDao().saveContactList(list.map {
            it.toEntity()
        })
    }

    override fun getContactListFromDB(): List<Contact> {
        return if (contactTableIsNotEmpty()){
            db.contactsDao().getContactList().map {
                it.toDomain()
            }
        }else emptyList()
    }

    override suspend fun getContactListFromApi(): List<Contact> {
        return if (NetworkConnection.isNetworkAvailable(appContext)){
            try {
                api.getContactList().contacts.map {
                    it.toDomain()
                }
            }catch (e: HttpException) {
                e.message?.let { Log.e("http", it) }
                emptyList()
            } catch (e: IOException) {
                e.message?.let { Log.e("IOException", it) }
                emptyList()
            }
        }else emptyList()
    }

    override fun editContact(contact: Contact) {
        db.contactsDao().updateContact(contact.toEntity())
    }

    override fun deleteContact(contact: Contact) {
        db.contactsDao().deleteContact(contact.toEntity())
    }

}