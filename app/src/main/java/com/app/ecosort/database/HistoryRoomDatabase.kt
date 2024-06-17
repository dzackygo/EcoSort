package com.app.ecosort.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.database.HistoryDao

@Database(entities = [History::class], version = 2)
abstract class HistoryRoomDatabase : RoomDatabase(){

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): HistoryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryRoomDatabase::class.java, "history_database")
                        .build()
                }
            }
            return INSTANCE as HistoryRoomDatabase
        }
    }

}