package com.example.tsfbanking.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsfbanking.databinding.TransactionItemLayoutBinding
import com.example.tsfbanking.pojo.Transaction

class TransactionsAdapter :
    ListAdapter<Transaction, TransactionsAdapter.ViewHolder>(TransactionItemDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransactionItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transactionId.text = "Trans.ID: ${getItem(position).id}"
        holder.binding.transactionFrom.text = "From: ${getItem(position).from}"
        holder.binding.transactionTo.text = "To: ${getItem(position).to}"
        holder.binding.transactionAmount.text = "${getItem(position).amount}$"
    }



    inner class ViewHolder(val binding: TransactionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TransactionItemDiff : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(
            oldItem: Transaction, newItem:
            Transaction
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }
}