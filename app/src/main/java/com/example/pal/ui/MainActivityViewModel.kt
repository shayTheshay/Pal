package com.example.pal.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pal.data.remote_db.Firebase.AuthRepository
import com.example.pal.data.remote_db.Firebase.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val petsRepository: PetsRepository
) :
    ViewModel() {

    // the user status
    var userStatus: Boolean = false

    var userFavorites: List<String> = emptyList()

    // the pet type Dog/Cat
    var petType: String = ""

    // set the animal type
    fun setAnimalType(type: String) {
        petType = type
    }

    fun updateFavorites(id: String) {
        viewModelScope.launch {
            userFavorites = petsRepository.addToFavorites(id)
        }
    }

    fun removePetFromFavorites(id: String) {
        viewModelScope.launch {
            userFavorites = petsRepository.removePetFromFavorites(id)
        }
    }

    // check with the repository about the user status
    init {
        viewModelScope.launch {
            userStatus = repository.checkUserStatus()
            if (userStatus) {
                userFavorites = repository.getUserFavorites()
            }
        }
    }

    // set the user status if he is logged in or guest
    @JvmName("setUserStatus1")
    fun setUserStatus(status: Boolean) {
        userStatus = status
    }

    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}