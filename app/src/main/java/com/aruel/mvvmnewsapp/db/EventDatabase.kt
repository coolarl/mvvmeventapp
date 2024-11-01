package com.aruel.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aruel.mvvmnewsapp.Events

@Database(
    entities = [Events::class],
    version = 1
)
abstract class EventDatabase : RoomDatabase() {

    abstract fun getArticleDao(): EventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "Event_db.db"
            ).build()
    }
}