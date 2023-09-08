package com.example.tsfbanking.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tsfbanking.pojo.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun doNewTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table")
    suspend fun getSpecificAccountTransactions():List<Transaction>
}