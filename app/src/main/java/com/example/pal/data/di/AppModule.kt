package com.example.pal.data.di

import android.content.Context
import com.example.pal.data.local_db.CatDatabase
import com.example.pal.data.local_db.DogDatabase
import com.example.pal.data.local_db.PetDatabase
import com.example.pal.data.remote_db.Firebase.AuthRepository
import com.example.pal.data.remote_db.Firebase.AuthRepositoryFirebase
import com.example.pal.data.remote_db.Firebase.PetsRepositoryFirebase
import com.example.pal.data.remote_db.Firebase.PetsRepository
import com.example.pal.data.remote_db.Retrofit.CatsService
import com.example.pal.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // provides the app dao and data bases of the room:

    @Provides
    @Singleton
    fun providePetLocalDataBase(@ApplicationContext appContext: Context): PetDatabase =
        PetDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideCatLocalDataBase(@ApplicationContext appContext: Context): CatDatabase =
        CatDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideDogLocalDataBase(@ApplicationContext appContext: Context): DogDatabase =
        DogDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun providePetDao(database: PetDatabase) = database.petDao()

    @Provides
    @Singleton
    fun provideCatDao(database: CatDatabase) = database.catDao()

    @Provides
    @Singleton
    fun provideDogDao(database: DogDatabase) = database.dogDao()

    // firebase providers
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // auth repo provider
    @Provides
    fun provideAuthRepositoryFirebase(firebaseAuth: FirebaseAuth):
            AuthRepository = AuthRepositoryFirebase(firebaseAuth)

    // pet repo provider
    @Provides
    fun providePetsRepositoryFirebase(): PetsRepository =
        PetsRepositoryFirebase(firebaseAuth = FirebaseAuth.getInstance())

    // Retrofit Provider, with default Gson build
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    // Gson provider
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    // provides the cat service class
    @Provides
    fun provideCatService(retrofit: Retrofit): CatsService =
        retrofit.create(CatsService::class.java)

}