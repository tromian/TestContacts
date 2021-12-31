package com.tromian.test.testcontacts.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tromian.test.testcontacts.R
import com.tromian.test.testcontacts.appComponent
import com.tromian.test.testcontacts.databinding.FragmentDetailsBinding
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.presentation.ViewModelsFactory
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var contact: Contact

    @Inject
    lateinit var repository: ContactRepository

    private val viewModel by viewModels<DetailsViewModel> {
        ViewModelsFactory(repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        val safeArgs: DetailsFragmentArgs by navArgs()
        contact = safeArgs.contact
        bindViews(contact)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews(contact: Contact) {
        Glide.with(this).load(contact.pictureUrl).into(binding.ivAvatar)
        binding.tvFirstName.text = contact.firstName
        binding.tvLastName.text = contact.lastName
        binding.tvPhone.text = contact.phone
        binding.tvEmail.text = contact.email

        binding.btnEdit.setOnClickListener {
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToEditDetailsFragment(contact)
            findNavController().navigate(action)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteContact(contact)
            findNavController().navigateUp()
        }
    }
}