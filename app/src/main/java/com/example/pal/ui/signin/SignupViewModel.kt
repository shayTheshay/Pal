package com.example.pal.ui.signin

import android.util.Patterns
import androidx.lifecycle.*
import com.example.pal.data.models.User
import com.example.pal.data.remote_db.Firebase.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.pal.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    // the user status
    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()

    // listener to the user status
    val userRegistrationStatus: LiveData<Resource<User>> = _userRegistrationStatus

    fun createUser(userName: String, userEmail: String, userPhone: String, userPass: String) {

        // check that the fields are not empty
        val error = if (userEmail.isNotEmpty() || userPass.isNotEmpty() ||
            userName.isNotEmpty() || userPhone.isEmpty()
        )
            "empty String"

        // check if the user email is valid
        else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
            "Not a valid Email"
        else if (userPass.length < 6)
            "password should have minimum 6 digits"
        else if (userPhone.length != 10)
            "Invalid phone number"
        else null

        // approach to firebase
        _userRegistrationStatus.postValue(Resource.loading())


        error?.let {
            // postValue was used for the option to make it on back thread
            _userRegistrationStatus.postValue(Resource.error(it))
        }

        // the viewModel scope is on the main coroutine,
        // but the coroutine that called inside will move it to back coroutine
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName, userEmail, userPhone, userPass)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }

}