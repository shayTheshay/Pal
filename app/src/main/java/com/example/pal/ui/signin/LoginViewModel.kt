package com.example.pal.ui.signin

import androidx.lifecycle.*
import com.example.pal.data.models.User
import com.example.pal.data.remote_db.Firebase.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.pal.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    // the user status
    private val _userSignInStatus = MutableLiveData<Resource<User>>()

    // expose access to the user status
    val userSignInStatus: LiveData<Resource<User>> = _userSignInStatus

    // the current user if someone is logged in
    private val _currentUser = MutableLiveData<Resource<User>>()

    // expose access to the user
    val currentUser: LiveData<Resource<User>> = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading()) // started to load the user
            _currentUser.postValue(repository.currentUser())
        }
    }

    fun signInUser(userEmail: String, userPass: String) {
        if (userEmail.isEmpty() || userPass.isEmpty()) {
            _userSignInStatus.postValue(Resource.error("Empty email or password\n please try again"))

        } else {
            _userSignInStatus.postValue(Resource.loading()) // started to load the user
            viewModelScope.launch {
                val loginResult = repository.login(userEmail, userPass)
                _userSignInStatus.postValue(loginResult)
            }
        }
    }

    fun signOut() {
        repository.logout()
    }

}