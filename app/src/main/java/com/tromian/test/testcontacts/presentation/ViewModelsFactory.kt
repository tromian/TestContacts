package com.tromian.test.testcontacts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.presentation.details.DetailsViewModel
import com.tromian.test.testcontacts.presentation.details.edit.EditViewModel
import com.tromian.test.testcontacts.presentation.main.MainViewModel
import javax.inject.Inject

class ViewModelsFactory(
    private val repository: ContactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MainViewModel::class.java -> {
                MainViewModel(repository)
            }
            DetailsViewModel::class.java -> {
                DetailsViewModel(repository)
            }
            EditViewModel::class.java -> {
                EditViewModel(repository)
            }
            else -> throw IllegalStateException("Unknown view model class")
        }
        return viewModel as T
    }
}