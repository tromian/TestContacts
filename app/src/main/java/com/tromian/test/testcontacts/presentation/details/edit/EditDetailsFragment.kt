package com.tromian.test.testcontacts.presentation.details.edit

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.tromian.test.testcontacts.utils.Patterns
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
        binding.inputFirstName.apply {
            setText(contact.firstName, TextView.BufferType.EDITABLE)
            addTextChangedListener(TextFieldValidation(this))
        }
        binding.inputLastName.apply {
            setText(contact.lastName, TextView.BufferType.EDITABLE)
            addTextChangedListener(TextFieldValidation(this))
        }

        binding.inputPhoneNumber.apply {
            setText(contact.phone, TextView.BufferType.EDITABLE)
            addTextChangedListener(TextFieldValidation(this))
        }

        binding.inputEmail.apply {
            setText(contact.email, TextView.BufferType.EDITABLE)
            addTextChangedListener(TextFieldValidation(this))
        }




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
        return if (isValidate()) {
            Contact(
                firstName = binding.inputFirstName.text.toString(),
                lastName = binding.inputLastName.text.toString(),
                phone = binding.inputPhoneNumber.text.toString(),
                email = binding.inputEmail.text.toString(),
                pictureUrl = contact.pictureUrl,
                id = contact.id
            )
        } else contact
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.inputFirstName -> {
                    validateFirstName()
                }
                R.id.inputLastName -> {
                    validateLastName()
                }
                R.id.inputEmail -> {
                    validateEmail()
                }
                R.id.inputPhoneNumber -> {
                    validatePhone()
                }
            }

        }

    }

    private fun isValidate(): Boolean =
        validateFirstName() && validateLastName() && validateEmail() && validatePhone()

    private fun validateFirstName(): Boolean {
        if (binding.inputFirstName.text.toString().trim().isEmpty()) {
            binding.firstNameInputLayout.error = "Required Field!"
            binding.inputFirstName.requestFocus()
            return false
        } else {
            binding.firstNameInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateLastName(): Boolean {
        if (binding.inputLastName.text.toString().trim().isEmpty()) {
            binding.lastNameInputLayout.error = "Required Field!"
            binding.inputLastName.requestFocus()
            return false
        } else {
            binding.lastNameInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.inputEmail.text.toString().trim().isEmpty()) {
            binding.emailInputLayout.error = "Required Field!"
            binding.inputEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding.inputEmail.text.toString().trim())) {
            binding.emailInputLayout.error = "Invalid Email!"
            binding.inputEmail.requestFocus()
            return false
        } else {
            binding.emailInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        if (binding.inputPhoneNumber.text.toString().trim().isEmpty()) {
            binding.phoneNumberInputLayout.error = "Required Field!"
            binding.inputPhoneNumber.requestFocus()
            return false
        } else if (!isValidPhone(binding.inputPhoneNumber.text.toString().trim())) {
            binding.phoneNumberInputLayout.error = "Invalid Email!"
            binding.inputPhoneNumber.requestFocus()
            return false
        } else {
            binding.phoneNumberInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Patterns.EMAIL_PATTERN.toRegex())
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.matches(Patterns.PHONE_NUMBER_PATTERN.toRegex())
    }
}