package com.tromian.test.testcontacts.domain

import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    fun saveContactsToDB(list: List<Contact>)

    fun getContactListFromDB() : List<Contact>

    suspend fun getContactListFromApi() : List<Contact>

    fun editContact(contact: Contact)

    fun deleteContact(contact: Contact)

}