package com.example.tsfbanking.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.pojo.Transaction
import com.example.tsfbanking.room.dao.AccountDao
import com.example.tsfbanking.room.dao.TransactionDao

@Database(entities = [Account::class, Transaction::class], version = 2, exportSchema = false)
abstract class AccountsDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AccountsDatabase? = null

        fun getDatabase(context: Context): AccountsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountsDatabase::class.java,
                    "accounts_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
