package com.example.pal.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pal.data.models.Pet

@Dao
interface PetDao {

    //if we add pet that already exist, replace it to the new one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(pet: List<Pet>)

    @Delete
    fun deletePet(pet: Pet)

    @Query("SELECT * FROM pets_table WHERE animal = :animal")
    fun getAllPets(animal: String): LiveData<List<Pet>>

    @Query("SELECT * FROM pets_table WHERE id = :id")
    fun getPet(id: Int): LiveData<Pet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)

    @Query("SELECT * FROM pets_table")
    fun getAllPets(): LiveData<List<Pet>>

    @Query("SELECT * FROM pets_table WHERE id IN (:ids)")
    fun getAllPetsById(ids: List<String>): LiveData<List<Pet>>

}