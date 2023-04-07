package com.example.pal.ui.homeScreens.search

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.example.pal.R
import com.example.pal.data.models.Cat
import com.example.pal.data.models.Dog
import com.example.pal.data.models.Pet
import com.example.pal.databinding.FragmentSinglePetInfoBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.util.Loading
import com.example.pal.util.Success

import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class SinglePetInfo : Fragment() {

    private var binding: FragmentSinglePetInfoBinding by autoCleared()

    private val viewModel: SinglePetInfoViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSinglePetInfoBinding.inflate(inflater, container, false)
        activityViewModel

//      the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        // the bottom menu ref,
        // and set the manu to be visible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = false

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // the dog rating category name
        val parameters = this.resources.getStringArray(R.array.parameters)

        if (activityViewModel.petType == "Dog") {
            viewModel.dog.observe(viewLifecycleOwner) {

                when (it.status) {

                    is Loading -> {
                        binding.infoPage.isVisible = false
                    }

                    is Success -> {
                        binding.infoPage.isVisible = true
                        binding.dogsOnlyStats.isVisible = true
                        binding.catsOnlyStats.isVisible = false
                        updateDog(it.status.data!!, parameters)
                    }

                    is Error -> {
                        binding.infoPage.isVisible = true
                        Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        } else {
            viewModel.cat.observe(viewLifecycleOwner) {

                when (it.status) {

                    is Loading -> {
                        binding.infoPage.isVisible = false
                    }

                    is Success -> {
                        binding.infoPage.isVisible = true
                        binding.dogsOnlyStats.isVisible = false
                        binding.catsOnlyStats.isVisible = true
                        updateCat(it.status.data!!, parameters)
                    }

                    is Error -> {
                        binding.infoPage.isVisible = true
                        Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        }

        arguments?.getString("breed")?.let {
            viewModel.setBreed(it, activityViewModel.petType)
        }
    }

    // update the dog breed info
    private fun updateDog(dog: Dog, parameters: Array<String>) {

        binding.breedDescribe.text = dog.Breed
        binding.weightDescribe.text = dog.Weight
        binding.lifeSpanDescribe.text = dog.Life_Span

        binding.sheddingRatingBar.Parameters.text = parameters[0]
        binding.sheddingRatingBar.ratingBar.rating = dog.Shedding.toFloat()

        binding.otherPetsFriendlyRatingBar.Parameters.text = parameters[1]
        binding.otherPetsFriendlyRatingBar.ratingBar.rating = dog.Other_Dogs_Friendly.toFloat()

        binding.childrenFriendlyRatingBar.Parameters.text = parameters[2]
        binding.childrenFriendlyRatingBar.ratingBar.rating = dog.Kid_Friendly.toFloat()

        binding.sizeRatingBar.Parameters.text = parameters[3]
        binding.sizeRatingBar.ratingBar.rating = dog.Size.toFloat()

        binding.droolingRatingBar.Parameters.text = parameters[4]
        binding.droolingRatingBar.ratingBar.rating = dog.Drooling_Level.toFloat()

        binding.toleratesBeingAloneRatingBar.Parameters.text = parameters[5]
        binding.toleratesBeingAloneRatingBar.ratingBar.rating = dog.Tolerates_Being_Alone.toFloat()

        binding.barkingRatingBar.Parameters.text = parameters[6]
        binding.barkingRatingBar.ratingBar.rating = dog.Barking_Level.toFloat()

        binding.apartmentLivingRatingBar.Parameters.text = parameters[7]
        binding.apartmentLivingRatingBar.ratingBar.rating = dog.Apartment_Living.toFloat()

        binding.easyToTrainRatingBar.Parameters.text = parameters[8]
        binding.easyToTrainRatingBar.ratingBar.rating = dog.Easy_To_Train.toFloat()

        binding.IntelligenceRatingBar.Parameters.text = parameters[9]
        binding.IntelligenceRatingBar.ratingBar.rating = dog.Intelligence.toFloat()

        Glide.with(requireContext()).load(dog.image).circleCrop().into(binding.petImage)
    }

    // update the dog breed info
    @SuppressLint("SetTextI18n")
    private fun updateCat(cat: Cat, parameters: Array<String>) {

        binding.breedDescribe.text = cat.name
        binding.weightDescribe.text = "${cat.max_weight} - ${cat.min_weight}"
        binding.lifeSpanDescribe.text = "${cat.max_life_expectancy} - ${cat.min_life_expectancy}"

        binding.sheddingRatingBar.Parameters.text = parameters[0]
        binding.sheddingRatingBar.ratingBar.rating = cat.shedding.toFloat()

        binding.otherPetsFriendlyRatingBar.Parameters.text = parameters[1]
        binding.otherPetsFriendlyRatingBar.ratingBar.rating = cat.other_pets_friendly.toFloat()

        binding.childrenFriendlyRatingBar.Parameters.text = parameters[2]
        binding.childrenFriendlyRatingBar.ratingBar.rating = cat.children_friendly.toFloat()

        binding.playfulnessRatingBar.Parameters.text = parameters[9]
        binding.playfulnessRatingBar.ratingBar.rating = cat.playfulness.toFloat()

        binding.groomingRatingBar.Parameters.text = parameters[10]
        binding.groomingRatingBar.ratingBar.rating = cat.grooming.toFloat()

        binding.meowingRatingBar.Parameters.text = parameters[11]
        binding.meowingRatingBar.ratingBar.rating = cat.meowing.toFloat()

        binding.generalHealthRatingBar.Parameters.text = parameters[12]
        binding.generalHealthRatingBar.ratingBar.rating = cat.general_health.toFloat()

        Glide.with(requireContext()).load(cat.image_link).circleCrop().into(binding.petImage)

    }

}