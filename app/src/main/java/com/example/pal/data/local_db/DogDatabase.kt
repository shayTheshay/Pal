package com.example.pal.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pal.data.models.Dog

@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {

        @Volatile
        private var instance: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {

            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "dogs"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}