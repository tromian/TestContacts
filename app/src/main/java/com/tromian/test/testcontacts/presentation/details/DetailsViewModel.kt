package com.tromian.test.testcontacts.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }
}