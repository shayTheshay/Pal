package com.example.pal.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pal.R
import com.example.pal.databinding.FragmentSignupBinding
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.util.Loading
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import com.example.pal.util.Resource
import com.example.pal.util.Success

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var binding: FragmentSignupBinding by autoCleared()

    private val viewModel: SignupViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignupBinding.inflate(inflater, container, false)

        // pass the data to the ViewModel
        binding.signInBtn.setOnClickListener {
            viewModel.createUser(
                binding.nameSignIn.text.toString(),
                binding.emailSignIn.text.toString(),
                binding.phoneSignIn.text.toString(),
                binding.passwordSignIn.text.toString()
            )
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.userRegistrationStatus.observe(viewLifecycleOwner) {

            when (it.status) {

                // when the user status is loading we will show the loading anim ui
                is Loading -> {
                    binding.signUpUi.isVisible = false
                    binding.signUpLoading.isVisible = true
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }

                // when the user status is success we will move to the next page and reset the ui
                is Success
                -> {
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT)
                        .show()

                    // set the user status true (marked as logged in)
                    activityViewModel.setUserStatus(true)
                    findNavController().navigate(R.id.action_signupFragment_to_entryFragment)
                    binding.signUpUi.isVisible = true
                    binding.signUpLoading.isVisible = false
                }

                // if the user status is failed we will pop up the message and wont change the ui
                else -> {
                    if (activityViewModel.checkForInternet(requireContext())) {
                        Toast.makeText(
                            requireContext(),
                            "Make sure the email is valid, and the password in at least 6 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                    }
                    binding.signUpUi.isVisible = true
                    binding.signUpLoading.isVisible = false
                }
            }
        }
    }
}