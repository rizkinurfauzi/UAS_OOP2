package com.rizkihanip.oop2.activity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizkihanip.oop2.Hanif.User
import com.rizkihanip.oop2.Hanif.UserDao
import com.rizkihanip.oop2.Rizki.Absen
import com.rizkihanip.oop2.Rizki.AbsenDao

@Database(
    entities = [Absen::class, User::class],
    version = 1
)
abstract class RoomDB : RoomDatabase(){

    abstract fun absenDao() : AbsenDao
    abstract fun userDao() : UserDao

    companion object {

        @Volatile private var instance : RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "absen"
        ).build()

    }
}