package com.tromian.test.testcontacts.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    val listContact = repository.getContactListFromDB()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadRemoteContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val remoteData = repository.getContactListFromApi()
            if (remoteData.isNotEmpty()) {
                repository.saveContactsToDB(remoteData)
            }
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }
}