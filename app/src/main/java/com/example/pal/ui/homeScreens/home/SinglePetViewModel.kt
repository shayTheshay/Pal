package com.example.pal.ui.homeScreens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pal.data.repository.PetRepositoryR
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SinglePetViewModel @Inject constructor(petRepositoryR: PetRepositoryR) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    // function that will store the pets we pressed
    val pet = Transformations.switchMap(_id) {
        petRepositoryR.getPet(it)
    }

    fun setId(id: Int) {
        _id.value = id
    }


}