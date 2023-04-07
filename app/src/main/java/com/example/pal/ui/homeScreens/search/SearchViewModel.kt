package com.example.pal.ui.homeScreens.search

import androidx.lifecycle.*
import com.example.pal.data.models.Dog
import com.example.pal.data.models.Pet
import com.example.pal.data.remote_db.Firebase.PetsRepository
import com.example.pal.data.repository.CatRepository
import com.example.pal.data.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.pal.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(dogsRep: DogsRepository, catRepo: CatRepository) :
    ViewModel() {

    val cats = catRepo.getCats()

    val dogs = dogsRep.getDogs()


}