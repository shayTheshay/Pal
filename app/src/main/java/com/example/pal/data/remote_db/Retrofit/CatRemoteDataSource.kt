package com.example.pal.data.remote_db.Retrofit

import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val catsService: CatsService
) : BaseDataSource() {

    // wrap the result, will invoke it wrap the return and the get cats will return the status
    suspend fun getCats() = getResult { catsService.getAllCats() }
    suspend fun getCat(name: String) = getResult { catsService.getCat(name) }

}