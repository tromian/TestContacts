package com.tromian.test.testcontacts.presentation.details.edit

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tromian.test.testcontacts.R
import com.tromian.test.testcontacts.appComponent
import com.tromian.test.testcontacts.databinding.FragmentDetailsEditBinding
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.presentation.ViewModelsFactory
import javax.inject.Inject

class EditDetailsFragment : Fragment(R.layout.fragment_details_edit) {

    private var _binding: FragmentDetailsEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var contact: Contact

    @Inject
    lateinit var repository: ContactRepository

    private val viewModel by viewModels<EditViewModel> {
        ViewModelsFactory(repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsEditBinding.bind(view)
        val safeArgs: EditDetailsFragmentArgs by navArgs()
        contact = safeArgs.contact
        bindViews(contact)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews(contact: Contact) {
        Glide.with(this).load(contact.pictureUrl).into(binding.ivAvatar)
        binding.inputFirstName.setText(contact.firstName, TextView.BufferType.EDITABLE)
        binding.inputLastName.setText(contact.lastName, TextView.BufferType.EDITABLE)
        binding.inputPhoneNumber.setText(contact.phone, TextView.BufferType.EDITABLE)
        binding.inputEmail.setText(contact.email, TextView.BufferType.EDITABLE)

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            val editedContact = getEditedContact()
            viewModel.editContact(editedContact)
            val action = EditDetailsFragmentDirections.actionEditDetailsFragmentToDetailsFragment(
                editedContact
            )
            findNavController().navigate(action)
        }
    }

    private fun getEditedContact(): Contact {
        return Contact(
            firstName = binding.inputFirstName.text.toString(),
            lastName = binding.inputLastName.text.toString(),
            phone = binding.inputPhoneNumber.text.toString(),
            email = binding.inputEmail.text.toString(),
            pictureUrl = contact.pictureUrl
        )
    }
}