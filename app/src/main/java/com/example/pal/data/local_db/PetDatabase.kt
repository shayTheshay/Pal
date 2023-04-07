package com.example.pal.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pal.data.models.Pet

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object {

        @Volatile
        private var instance: PetDatabase? = null

        fun getDatabase(context: Context): PetDatabase {

            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PetDatabase::class.java,
                    "pets_table"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}