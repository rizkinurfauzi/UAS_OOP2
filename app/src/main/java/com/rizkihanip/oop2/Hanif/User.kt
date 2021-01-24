package com.rizkihanip.oop2.Hanif

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "alamat") val alamat: String,
    @ColumnInfo(name = "username") val username: String
)