package com.example.pal.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.pal.data.models.Dog

@Dao
interface DogDao {

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): LiveData<List<Dog>>

    @Query("SELECT * FROM dogs WHERE Breed = :breed")
    fun getDog(breed: String): LiveData<Dog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: Dog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<Dog>)

}