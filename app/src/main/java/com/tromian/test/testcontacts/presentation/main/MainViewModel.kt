package com.tromian.test.testcontacts.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.utils.Loading
import com.tromian.test.testcontacts.utils.Result
import com.tromian.test.testcontacts.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

//    private val _dataResult: MutableLiveData<Result<List<Contact>>> = MutableLiveData(Loading())
//    val dataResult: LiveData<Result<List<Contact>>> = _dataResult

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

    private fun updateLiveData(){
        viewModelScope.launch {
            val localContacts = repository.getContactListFromDB()
            if (localContacts.isNotEmpty()){
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

    fun editContact(contact: Contact) {
        viewModelScope.launch {
            repository.editContact(contact)
            updateLiveData()
        }
    }

}