package com.example.pal.ui.homeScreens.settings.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pal.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private var binding: FragmentChangePasswordBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        // the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }

}