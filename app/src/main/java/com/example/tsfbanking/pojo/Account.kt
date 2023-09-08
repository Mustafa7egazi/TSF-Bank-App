package com.example.tsfbanking.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "accounts_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val email:String,
    val balance:Double
):Parcelable
