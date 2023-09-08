package com.example.tsfbanking.reposiory

import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.pojo.Transaction
import com.example.tsfbanking.room.dao.AccountDao
import com.example.tsfbanking.room.dao.TransactionDao

class Repository(private val dao:AccountDao,private val transactionDao: TransactionDao) {

    suspend fun createNewAccount(account: Account){
        dao.createNewAccount(account)
    }

    suspend fun getAllAccounts():List<Account>{
        return dao.getAllAccounts()
    }

//      fun getAccountByName(name:String):Account{
//        return dao.getAccountByName(name)
//    }

    suspend fun updateAccount(account: Account){
        dao.updateAccount(account)
    }

    suspend fun doNewTransaction(transaction: Transaction){
        transactionDao.doNewTransaction(transaction)
    }

    suspend fun getSpecificAccountTransactions():List<Transaction>{
        return transactionDao.getSpecificAccountTransactions()
    }

}