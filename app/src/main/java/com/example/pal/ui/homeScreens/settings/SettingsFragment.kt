package com.example.pal.ui.homeScreens.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pal.R
import com.example.pal.databinding.FragmentSettingsBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.ui.signin.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding by autoCleared()

    private val viewModel: LoginViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // the settings buttons names
        val settingsButtonsNames = this.resources.getStringArray(R.array.settings)

        // the right arrow on the buttons
        val rightDrawable =
            getDrawable(requireContext(), R.drawable.ic_baseline_arrow_forward_ios_24)

        // the bottom menu ref, and set the manu to be visible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = true


        // set the buttons that will appear always in this page
        binding.settingsTerms.settingsBtnText.text = settingsButtonsNames[1]
        binding.settingsContactUs.settingsBtnText.text = settingsButtonsNames[0]


        // Contact Us
        binding.settingsContactUs.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
            getDrawable(requireContext(), R.drawable.ic_baseline_alternate_email_24),
            null, rightDrawable, null
        )

        // Terms
        binding.settingsTerms.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
            getDrawable(requireContext(), R.drawable.ic_baseline_terms_file_24),
            null, rightDrawable, null
        )

        // terms button
        binding.settingsTerms.settingsBtn.setOnClickListener {

            findNavController().navigate(R.id.action_SettingsFragment_to_termsFragment)
            navigationBar.isVisible = false
        }

        // contact us button
        binding.settingsContactUs.settingsBtn.setOnClickListener {

            findNavController().navigate(R.id.action_SettingsFragment_to_contactUsFragment)
            navigationBar.isVisible = false
        }

        binding.user.isVisible = activityViewModel.userStatus
        binding.guest.isVisible = !activityViewModel.userStatus

        // check the user status to initialize the necessary buttons
        // check if the user is logged in
        if (activityViewModel.userStatus) {

            // set the buttons names
            binding.settingsChangePassword.settingsBtnText.text = settingsButtonsNames[2]
            binding.settingsDeleteUser.settingsBtnText.text = settingsButtonsNames[3]
            binding.settingsSignOut.settingsBtnText.text = settingsButtonsNames[4]

            // set the buttons drawables

            // Change password
            binding.settingsChangePassword.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(requireContext(), R.drawable.ic_baseline_lock_24),
                null, rightDrawable, null
            )

            // Delete user
            binding.settingsDeleteUser.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(requireContext(), R.drawable.ic_baseline_delete_24),
                null, rightDrawable, null
            )

            // Sign out
            binding.settingsSignOut.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(requireContext(), R.drawable.ic_baseline_logout_24),
                null, rightDrawable, null
            )

            // set the buttons actions

            // change password button
            binding.settingsChangePassword.settingsBtn.setOnClickListener {

                findNavController().navigate(R.id.action_SettingsFragment_to_changePasswordFragment)
                navigationBar.isVisible = false
            }

            // delete user button
            binding.settingsDeleteUser.settingsBtn.setOnClickListener {
                Toast.makeText(
                    context,
                    "We are working on it, contact us to delete the user",
                    Toast.LENGTH_LONG
                ).show()
            }

            // sign out button
            binding.settingsSignOut.settingsBtn.setOnClickListener {
                viewModel.signOut()
                activityViewModel.setUserStatus(false)
                findNavController().navigate(R.id.action_SettingsFragment_to_loginFragment)
                Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
            }

        } else {
            // if the user is in guest mode
            binding.settingsLogIn.settingsBtnText.text = settingsButtonsNames[5]

            // Log in
            binding.settingsLogIn.settingsBtnText.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(requireContext(), R.drawable.ic_baseline_login_24),
                null, rightDrawable, null
            )

            // Log in button
            binding.settingsLogIn.settingsBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SettingsFragment_to_loginFragment)
            }
        }

        return binding.root
    }

}