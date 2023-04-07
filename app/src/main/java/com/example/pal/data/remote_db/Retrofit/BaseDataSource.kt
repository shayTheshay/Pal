package com.example.pal.data.remote_db.Retrofit

import com.example.pal.util.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T>
            getResult(call : suspend () -> Response<T>) : Resource<T> {

        try {
            val result  = call()
            if(result.isSuccessful) {
                val body = result.body()
                if(body != null) return  Resource.success(body)
            }
            return Resource.error("Network call has failed for the following reason: " +
                    "${result.message()} ${result.code()}")
        } catch (e : Exception) {
            return Resource.error("Network call has failed for the following reason: "
             + (e.localizedMessage ?: e.toString()))
        }
    }
}