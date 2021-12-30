package com.tromian.test.testcontacts.presentation.details.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import kotlinx.coroutines.launch

class EditViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    fun editContact(contact: Contact) {
        viewModelScope.launch {
            repository.editContact(contact)
        }
    }
}