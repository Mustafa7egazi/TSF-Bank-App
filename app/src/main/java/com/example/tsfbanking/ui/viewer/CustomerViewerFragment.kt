package com.example.tsfbanking.ui.viewer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsfbanking.R
import com.example.tsfbanking.adapter.TransactionsAdapter
import com.example.tsfbanking.databinding.FragmentCustomerViewerBinding
import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.pojo.Transaction
import com.example.tsfbanking.viewmodel.BankViewModel

class CustomerViewerFragment : Fragment() {

    private lateinit var binding: FragmentCustomerViewerBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[BankViewModel::class.java]
    }

    private val args: CustomerViewerFragmentArgs by navArgs()
    private var isClicked: Boolean = false
    private var transactionsList = listOf<Transaction>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_customer_viewer,
            container,
            false
        )

        binding.ownerId.text = "ID: ${args.ownerId}"
        binding.accountOwner.text = "Account Owner: ${args.ownerName}"
        binding.ownerEmail.text = "Owner Email: ${args.ownerMail}"
        binding.ownerBalance.text = "Current Balance: ${args.ownerBalance}$"

        try {
            viewModel.getSpecificAccountTransactions()
        } catch (e: Exception) {
            Log.d("7egzz", e.message.toString())
        }

        val listy = arrayListOf("Select").also {
            for (i in args.allCustomerList) {
                if (i.name == binding.accountOwner.text) {
                    continue
                }
                it.add(i.name)
            }
        }
        binding.receiversSpinner.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            listy
        )


        binding.transferBtn.setOnClickListener {
            if (!isClicked) {
                isClicked = true
                binding.transferLayout.visibility = View.VISIBLE
                binding.transferBtn.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.materialPrimaryColor)
                binding.transferBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                isClicked = false
                binding.transferLayout.visibility = View.INVISIBLE
                binding.transferBtn.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.transferBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.materialPrimaryColor
                    )
                )
            }
        }

        binding.confirmTransactionBtn.setOnClickListener {
            val amount = binding.amountET.text.toString().toDoubleOrNull()
            val selectedReceiver = binding.receiversSpinner.selectedItem.toString()
            if (amount != null) {
                if (selectedReceiver != "Select") {
                    val sendingAccount = args.allCustomerList.find { it.name == args.ownerName }
                    val receivingAccount = args.allCustomerList.find { it.name == selectedReceiver }


                    if (sendingAccount != null && receivingAccount != null) {
                        val senderRemainingBalance = sendingAccount.balance - amount
                        val receiverRemainingBalance = receivingAccount.balance + amount

                        if (senderRemainingBalance < 0) {
                            Toast.makeText(
                                requireContext(),
                                "No enough balance!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            val dialog = AlertDialog.Builder(requireContext())
                            dialog.apply {
                                setTitle("Confirm transaction")
                                setMessage("Are you sure to transfer $amount to $selectedReceiver")
                                setPositiveButton("Yes") { _, _ ->
                                    val updatedSendingAccount = Account(
                                        sendingAccount.id,
                                        sendingAccount.name,
                                        sendingAccount.email,
                                        senderRemainingBalance
                                    )
                                    val updatedReceivingAccount = Account(
                                        receivingAccount.id,
                                        receivingAccount.name,
                                        receivingAccount.email,
                                        receiverRemainingBalance
                                    )

                                    try {
                                        viewModel.updateAccount(updatedSendingAccount)
                                        viewModel.updateAccount(updatedReceivingAccount)
                                        Toast.makeText(
                                            requireContext(),
                                            "Success transaction!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val newTransaction = Transaction(
                                            id = 0,
                                            from = args.ownerName,
                                            to = selectedReceiver,
                                            amount = amount
                                        )

                                        Log.d("7egzz", "$newTransaction")

                                        viewModel.doNewTransaction(newTransaction)

                                        Navigation.findNavController(binding.root)
                                            .navigate(R.id.action_customerViewerFragment_to_customersFragment)

                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            requireContext(),
                                            e.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                setNegativeButton("Cancel") { _, _ ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Transaction canceled!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "$sendingAccount \n $receivingAccount",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please select a receiver!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "$amount", Toast.LENGTH_SHORT).show()
            }
        }

        binding.transactionHistory.setOnClickListener {
            try {
                val currentUserTransactions =
                    transactionsList.filter { it.from == args.ownerName || it.to == args.ownerName }
                showRecyclerViewDialog(requireContext(), currentUserTransactions)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.gettingAllTransactionStatus.observe(viewLifecycleOwner) {
            if (it != null) {
                transactionsList = it
            }
        }

        return binding.root
    }

    private fun showRecyclerViewDialog(context: Context, itemList: List<Transaction>) {
        val alertDialog = AlertDialog.Builder(context).create()

        if (itemList.isEmpty()) {
            alertDialog.setMessage("No transactions yet!\n$itemList")
        } else {
            val recyclerView = RecyclerView(context)
            recyclerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager

            val adapter = TransactionsAdapter().apply {
                submitList(itemList)
            }
            recyclerView.adapter = adapter

            alertDialog.setView(recyclerView)
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}