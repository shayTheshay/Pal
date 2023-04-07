package com.example.pal.data.repository

import com.example.pal.data.local_db.DogDao
import com.example.pal.data.remote_db.Firebase.PetsRepositoryFirebase
import com.example.pal.util.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogsRepository @Inject constructor(
    private val remoteDataSource: PetsRepositoryFirebase,
    private val localDataSource: DogDao
) {

    fun getDogs() = performFetchingAndSaving(
        { localDataSource.getAllDogs() },
        { remoteDataSource.getDogs() },
        { localDataSource.insertDogs(it) }
    )

    fun getDog(breed: String) = performFetchingAndSaving(
        { localDataSource.getDog(breed) },
        { remoteDataSource.findDogByBreed(breed) },
        { localDataSource.insertDog(it) }
    )

}