package com.example.pal.ui.homeScreens.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pal.R
import com.example.pal.databinding.FragmentHomeBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.util.Loading
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import com.example.pal.util.Resource
import com.example.pal.util.Success

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()

    private val viewModel: HomeViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    private var imageList = ArrayList<SlideModel>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // the gallery description
        val galleryDescription: String = this.resources.getString(R.string.our_angels)

        // get the pets type by the user press in the entry fragment
        viewModel.getPets(activityViewModel.petType)

        // set the 6 slider images and there titles
        imageList.add(SlideModel(R.drawable.home_slider1, galleryDescription))
        imageList.add(SlideModel(R.drawable.home_slider2, galleryDescription))
        imageList.add(SlideModel(R.drawable.home_slider3, galleryDescription))
        imageList.add(SlideModel(R.drawable.home_slider4, galleryDescription))
        imageList.add(SlideModel(R.drawable.home_slider5, galleryDescription))
        imageList.add(SlideModel(R.drawable.home_slider6, galleryDescription))

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        // fill the home slider with the images
        binding.imageHomeSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        // the bottom menu ref, and set the manu to be visible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = true

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecycler.layoutManager = LinearLayoutManager(requireContext())


        binding.homeRecycler.adapter = HomeAdapter(object : HomeAdapter.PetsListener {
            override fun onPetClicked(index: Int, petId: Int) {

                imageList.clear()
                //when pressing on a pet we passing the id of the dog to single pet fragment
                findNavController().navigate(
                    R.id.action_homeFragment_to_singlePet,
                    bundleOf("petId" to petId)
                )
            }
        })

        viewModel.petBen.observe(viewLifecycleOwner) {
            when (it.status) {

                // when the user status is loading we will show the loading anim ui
                is Loading -> {
                    binding.homeUi.isVisible = false
                    binding.homeLoading.isVisible = true
                }

                // when the user status is success we will move to the next page and reset the ui
                is Success -> {
                    binding.homeUi.isVisible = true
                    binding.homeLoading.isVisible = false
                    (binding.homeRecycler.adapter as HomeAdapter).setPets(it.status.data!!)
                }

                // if the user status is failed we will pop up the message and wont change the ui
                is Error -> {
                    binding.homeUi.isVisible = true
                    binding.homeLoading.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}