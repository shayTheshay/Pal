package com.example.pal.ui.homeScreens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pal.data.repository.CatRepository
import com.example.pal.data.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SinglePetInfoViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val catRepository: CatRepository
) : ViewModel() {

    private val _dogBreed = MutableLiveData<String>()
    private val _catBreed = MutableLiveData<String>()

    val dog = Transformations.switchMap(_dogBreed) {
        dogsRepository.getDog(it)
    }

    val cat = Transformations.switchMap(_catBreed) {
        catRepository.getCat(it)
    }

    fun setBreed(breed: String, petType: String) {
        if (petType == "Dog") {
            _dogBreed.value = breed
        } else {
            _catBreed.value = breed
        }
    }
}