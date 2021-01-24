package com.rizkihanip.oop2.Rizki

import androidx.room.*

@Dao
interface AbsenDao {
    @Insert
    suspend fun addAbsen(absen: Absen)

    @Query("SELECT * FROM absen ORDER BY id DESC")
    suspend fun getAbs() : List<Absen>

    @Query("SELECT * FROM absen WHERE id=:absen_id")
    suspend fun getAbsen(absen_id: Int) : List<Absen>

    @Update
    suspend fun updateAbsen(absen: Absen)

    @Delete
    suspend fun deleteAbsen(absen: Absen)
}