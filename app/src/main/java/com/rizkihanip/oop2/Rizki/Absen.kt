package com.rizkihanip.oop2.Rizki

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Absen(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nama: String,
    val nim_kelas: String
)