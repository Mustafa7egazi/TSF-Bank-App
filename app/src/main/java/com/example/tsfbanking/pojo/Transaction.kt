package com.example.tsfbanking.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val from:String,
    val to:String,
    val amount:Double
)
