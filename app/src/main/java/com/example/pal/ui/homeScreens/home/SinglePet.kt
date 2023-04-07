package com.example.pal.ui.homeScreens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pal.R
import com.example.pal.data.models.Pet
import com.example.pal.databinding.FragmentSinglePetBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.util.Loading
import com.example.pal.util.Success
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class SinglePet : Fragment() {

    private var binding: FragmentSinglePetBinding by autoCleared()

    private val viewModel: SinglePetViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSinglePetBinding.inflate(inflater, container, false)

        binding.guest.isVisible = !activityViewModel.userStatus
        binding.favorites.isVisible = activityViewModel.userStatus

//      the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        // the bottom menu ref, and set the manu to be visible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = false

        // if the user is not in guest mode
        if (activityViewModel.userStatus) {

            // set listener to the add to favorites image button
            binding.checkboxFavorites.setOnCheckedChangeListener { checkBox, isChecked ->

                if (isChecked) {
                    if (checkBox.isPressed) {
                        activityViewModel.updateFavorites(binding.id.text.toString())
                        Toast.makeText(
                            requireContext(),
                            "Pet added to your favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    activityViewModel.removePetFromFavorites(binding.id.text.toString())
                    Toast.makeText(
                        requireContext(),
                        "Pet Removed from your favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pet.observe(viewLifecycleOwner) {
            when (it.status) {

                is Loading -> {
                    binding.homeLoading.isVisible = true
                    binding.guest.isVisible = false
                    binding.favorites.isVisible = false
                }

                is Success -> {
                    binding.homeLoading.isVisible = false
                    binding.guest.isVisible = !activityViewModel.userStatus
                    binding.favorites.isVisible = activityViewModel.userStatus
                    updatePet(it.status.data!!)//send to this function the pet to update the detail
                }
                else -> {
                    binding.homeLoading.isVisible = false
                    binding.guest.isVisible = !activityViewModel.userStatus
                    binding.favorites.isVisible = activityViewModel.userStatus
                }
            }
        }
        // getting the id of the pet after we pressed the recycler view item
        arguments?.getInt("petId")?.let {

            //set the id in the view model
            viewModel.setId(it)
        }
    }

    // update the pet information, set the necessary fields in the fragment
    private fun updatePet(pet: Pet) {

        binding.name.text = pet.name
        binding.sex.text = pet.sex
        binding.age.text = pet.age
        binding.description.text = pet.description
        binding.breed.text = pet.breed
        Glide.with(requireContext()).load(pet.pic).circleCrop().into(binding.image)
        binding.id.text = pet.id.toString()

        if (activityViewModel.userStatus) {
            for (id in activityViewModel.userFavorites) {

                if (id == pet.id.toString()) {
                    binding.checkboxFavorites.isChecked = true
                }
            }
        }
    }
}