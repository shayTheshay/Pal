package com.example.pal.ui.entry


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pal.R
import com.example.pal.databinding.FragmentEntryBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel

import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class EntryFragment : Fragment() {

    private var binding: FragmentEntryBinding by autoCleared()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEntryBinding.inflate(inflater, container, false)

        // the bottom menu ref, and set the manu to be invisible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = false

        // the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        // on press moving to the dog section (Home screen with dogs for adoption)
        binding.dogSection.setOnClickListener {

            //set the animal type to dog
            activityViewModel.setAnimalType("Dog")
            findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
        }

        // on press moving to the cat section (Home screen with cats for adoption)
        binding.catSection.setOnClickListener {

            //set the animal type to cat
            activityViewModel.setAnimalType("Cat")
            findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
        }
        return binding.root
    }
}