package com.example.tsfbanking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.pojo.Transaction
import com.example.tsfbanking.reposiory.Repository
import com.example.tsfbanking.room.dao.AccountDao
import com.example.tsfbanking.room.dao.TransactionDao
import com.example.tsfbanking.room.database.AccountsDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BankViewModel(application: Application): AndroidViewModel(application) {


    private  var accountDao:AccountDao
    private  var transactionDao:TransactionDao
    private  var repo:Repository

    init {
        accountDao = AccountsDatabase.getDatabase(application.applicationContext).accountDao()
        transactionDao = AccountsDatabase.getDatabase(application.applicationContext).transactionDao()

        repo = Repository(accountDao,transactionDao)

    }

    private var _allSavedAccounts = MutableLiveData<List<Account>>()
     val allSavedAccounts:LiveData<List<Account>>
         get() = _allSavedAccounts


    private var _gettingAllTransactionStatus = MutableLiveData<List<Transaction>>()
    val gettingAllTransactionStatus:LiveData<List<Transaction>>
        get() = _gettingAllTransactionStatus


    fun getAllAccounts(){
        viewModelScope.launch(Dispatchers.IO){
            val result = repo.getAllAccounts()
            withContext(Dispatchers.Main){
                _allSavedAccounts.value = result
            }
        }

    }

//     fun getAccountByName(name:String): Account {
//       return repo.getAccountByName(name)
//    }


    fun updateAccount(account: Account){
       viewModelScope.launch(Dispatchers.IO){
           repo.updateAccount(account)
       }
    }

    fun doNewTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO){
            repo.doNewTransaction(transaction)
        }
    }


    fun getSpecificAccountTransactions(){
        viewModelScope.launch(Dispatchers.IO){
            val result = repo.getSpecificAccountTransactions()
            withContext(Dispatchers.Main){
                _gettingAllTransactionStatus.value = result
            }
        }
    }
}