package com.example.pal.data.repository


import com.example.pal.data.local_db.PetDao
import com.example.pal.data.remote_db.Firebase.PetsRepositoryFirebase
import com.example.pal.util.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetRepositoryR @Inject constructor(
    private val remoteDataSource: PetsRepositoryFirebase,
    private val localDataSource: PetDao
) {

    fun getPets(animal: String) = performFetchingAndSaving(
        { localDataSource.getAllPets(animal) },
        { remoteDataSource.getPets(animal) },
        { localDataSource.insertPets(it) }
    )

    fun getPet(id: Int) = performFetchingAndSaving(
        { localDataSource.getPet(id) },
        { remoteDataSource.getPet(id) },
        { localDataSource.insertPet(it) }
    )

}



