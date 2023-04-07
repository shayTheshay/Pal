package com.example.pal.ui.signin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pal.R
import com.example.pal.databinding.FragmentLoginBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.util.Loading
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import com.example.pal.util.Resource
import com.example.pal.util.Success

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // the bottom menu ref, and set the manu to be invisible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = false

        // user enter the application without an account
        binding.guestBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_entryFragment)
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_changePasswordFragment)
        }

        // users without account will press here and move to the sign up page
        binding.signupBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        // login button
        binding.loginBtn.setOnClickListener {

            viewModel.signInUser(
                binding.emailLogin.text.toString(),
                binding.passwordLogin.text.toString()
            )
        }

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSignInStatus.observe(viewLifecycleOwner) {
            when (it.status) {

                // when the user status is loading we will show the loading anim ui
                is Loading -> {
                    binding.loginUi.isVisible = false
                    binding.loginLoading.isVisible = true
                }

                // when the user status is success we will move to the next page and reset the ui
                is Success -> {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    // set the user status true (marked as logged in)
                    activityViewModel.setUserStatus(true)
                    findNavController().navigate(R.id.action_loginFragment_to_entryFragment)
                    binding.loginUi.isVisible = true
                    binding.loginLoading.isVisible = false
                }

                // if the user status is failed we will pop up the message and wont change the ui
                else -> {

                    if(activityViewModel.checkForInternet(requireContext())) {
                        Toast.makeText(requireContext(), "Email or Password are wrong", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                    }

                    binding.loginUi.isVisible = true
                    binding.loginLoading.isVisible = false
                }
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            when (it.status) {
                is Success -> { //if the user is still login and didn't sign-out
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_entryFragment)
                    binding.loginUi.isVisible = true
                    binding.loginLoading.isVisible = false
                }
                else -> {}
            }
        }
    }

}