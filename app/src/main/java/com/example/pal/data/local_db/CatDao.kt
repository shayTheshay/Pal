package com.example.pal.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pal.data.models.Cat


@Dao
interface CatDao {

    @Query("SELECT * FROM cat_table")
    fun getAllCats(): LiveData<List<Cat>>

    @Query("SELECT * FROM cat_table WHERE name = :name")
    fun getCat(name: String): LiveData<Cat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cat>)

}