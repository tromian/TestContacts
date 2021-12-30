package com.tromian.test.testcontacts.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val localContacts = repository.getContactListFromDB()
            if (localContacts.isNotEmpty()){
                _contacts.postValue(localContacts)
            }else {
                loadRemoteContacts()
            }
        }
    }

    fun updateLiveData() {
        viewModelScope.launch {
            val localContacts = repository.getContactListFromDB()
            if (localContacts.isNotEmpty()) {
                _contacts.postValue(localContacts)
            }
        }
    }

    fun loadRemoteContacts(){
        viewModelScope.launch(Dispatchers.IO) {
            val remoteData = repository.getContactListFromApi()
            if (remoteData.isNotEmpty()){
                repository.saveContactsToDB(remoteData)
                updateLiveData()
            }
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            repository.deleteContact(contact)
            updateLiveData()
        }
    }
}