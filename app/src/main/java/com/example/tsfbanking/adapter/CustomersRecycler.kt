package com.example.tsfbanking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsfbanking.databinding.CustomerItemLayoutBinding
import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.ui.customers.CustomersFragmentDirections
import com.example.tsfbanking.ui.viewer.CustomerViewerFragment

class CustomersAdapter : ListAdapter<Account, CustomersAdapter.ViewHolder>(AccountItemDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CustomerItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.personName.text = getItem(position).name
        holder.binding.personEmail.text = getItem(position).email
        holder.binding.personID.text = getItem(position).id.toString()
        holder.binding.customerItemCard.setOnClickListener {
            val listToPass = currentList.toTypedArray()
            val item = getItem(position)
            val action =
                CustomersFragmentDirections.actionCustomersFragmentToCustomerViewerFragment(
                    allCustomerList = listToPass,
                    ownerId = item.id,
                    ownerBalance = item.balance.toString(),
                    ownerMail = item.email,
                    ownerName = item.name
                )
            Navigation.findNavController(holder.binding.root).navigate(action)
        }

    }


    inner class ViewHolder(val binding: CustomerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class AccountItemDiff : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(
            oldItem: Account, newItem:
            Account
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }

    }
}