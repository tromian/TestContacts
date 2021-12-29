package com.tromian.test.testcontacts.data

import android.app.Application
import com.tromian.test.testcontacts.data.db.ContactsDB
import com.tromian.test.testcontacts.data.network.RandomUserApi
import com.tromian.test.testcontacts.domain.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val localDB: ContactsDB,
    private val weatherApi: RandomUserApi,
    private val appContext: Application,
) : ContactRepository {

}