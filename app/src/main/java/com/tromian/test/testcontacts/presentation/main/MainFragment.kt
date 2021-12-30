package com.tromian.test.testcontacts.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tromian.test.testcontacts.R
import com.tromian.test.testcontacts.appComponent
import com.tromian.test.testcontacts.databinding.FragmentMainBinding
import com.tromian.test.testcontacts.domain.Contact
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.presentation.ViewModelsFactory
import com.tromian.test.testcontacts.utils.Success
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: ContactRepository

    private val viewModel by viewModels<MainViewModel> {
        ViewModelsFactory(repository)
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ContactListAdapter() { itemId ->
            openFragment(itemId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        val rvContactList = binding.rvMain
        rvContactList.adapter = adapter

        val swipeCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteItem(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rvContactList)
        viewModel.contacts.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteItem(id: Int){
        val contact = adapter.currentList[id]
        viewModel.deleteContact(contact)
    }
    private fun openFragment(id: Int){
        val contact = adapter.currentList[id]
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(contact)
        findNavController().navigate(action)
    }
}