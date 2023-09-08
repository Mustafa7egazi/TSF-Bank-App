package com.example.tsfbanking.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tsfbanking.pojo.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewAccount(account: Account)

    @Query("SELECT * FROM accounts_table ORDER BY id ASC")
    suspend fun getAllAccounts(): List<Account>

//    @Query("SELECT * FROM accounts_table WHERE name= :ownerName")
//    fun getAccountByName(ownerName: String): Account

    @Update
    suspend fun updateAccount(account: Account)

}