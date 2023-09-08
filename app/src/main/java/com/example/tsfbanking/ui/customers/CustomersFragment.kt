package com.example.tsfbanking.ui.customers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tsfbanking.R
import com.example.tsfbanking.adapter.CustomersAdapter
import com.example.tsfbanking.databinding.FragmentCustomersBinding
import com.example.tsfbanking.viewmodel.BankViewModel

class CustomersFragment : Fragment() {

    private lateinit var binding: FragmentCustomersBinding
    private val viewModel: BankViewModel by lazy {
        ViewModelProvider(this)[BankViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_customers, container, false)

        viewModel.getAllAccounts()
        viewModel.allSavedAccounts.observe(viewLifecycleOwner) { accountsList ->
            if (accountsList.isNotEmpty()) {
                val customersAdapter = CustomersAdapter().apply {
                    submitList(accountsList)
                }

                binding.customersRecycler.adapter = customersAdapter
            } else {
                Toast.makeText(requireContext(), "Empty customer list", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        return binding.root
    }
}