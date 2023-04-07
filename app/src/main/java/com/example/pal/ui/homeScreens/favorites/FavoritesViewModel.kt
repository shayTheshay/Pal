package com.example.pal.ui.homeScreens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pal.data.models.Pet

import com.example.pal.data.remote_db.Firebase.PetsRepository
import com.example.pal.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val petsRepository: PetsRepository) :
    ViewModel() {


    // the dogs ref
    private val _pets: MutableLiveData<Resource<List<Pet>>> = MutableLiveData()

    // the dogs ref we expose
    val pets: LiveData<Resource<List<Pet>>> = _pets

    // set the dogs array with the get dogs fun from the Pet repository
    fun getPets(ids: List<String>) {
        viewModelScope.launch {
            _pets.value = Resource.loading()
            _pets.value = Resource.success(petsRepository.getAllPetsById(ids))
        }
    }
}