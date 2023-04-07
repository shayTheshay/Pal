package com.example.pal.data.repository

import com.example.pal.data.local_db.CatDao
import com.example.pal.data.remote_db.Retrofit.CatRemoteDataSource
import com.example.pal.util.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val localDataSource: CatDao
) {

    fun getCats() = performFetchingAndSaving(
        { localDataSource.getAllCats() },
        { remoteDataSource.getCats() },
        { localDataSource.insertCats(it) }
    )

    fun getCat(name: String) = performFetchingAndSaving(
        { localDataSource.getCat(name) },
        { remoteDataSource.getCat(name) },
        { localDataSource.insertCat(it) }
    )

}