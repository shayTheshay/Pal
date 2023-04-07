package com.example.pal.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pal.data.models.Cat

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    companion object {
        // no cashing
        @Volatile
        private var instance: CatDatabase? = null

        fun getDatabase(context: Context): CatDatabase {

            // using synchronized to avoid race condition, with this lock
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "cat_table"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}