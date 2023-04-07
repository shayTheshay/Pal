package com.example.pal.data.remote_db.Firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pal.data.models.Dog
import com.example.pal.data.models.Pet
import com.example.pal.util.Resource

interface PetsRepository {

    // get dog info by breed (from dogs collection)
    suspend fun findDogByBreed(breed: String): Resource<Dog>

    // get all pets to the home screen by animal type(dpg / cat)
    suspend fun getPets(animal: String): Resource<List<Pet>>

    // get dog info by id (on press of dog from the home screen)
    suspend fun getPet(id: Int): Resource<Pet>

    // get the dog info by breed
    suspend fun getDogs(): Resource<List<Dog>>

    suspend fun getAllPetsById(ids: List<String>): List<Pet>

    suspend fun addToFavorites(id: String): List<String>

    suspend fun removePetFromFavorites(id: String): List<String>

}