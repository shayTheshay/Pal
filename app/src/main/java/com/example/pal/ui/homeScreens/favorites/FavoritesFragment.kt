package com.example.pal.ui.homeScreens.favorites

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
import com.example.pal.R
import com.example.pal.databinding.FragmentFavoritesBinding
import com.example.pal.ui.MainActivity
import com.example.pal.ui.MainActivityViewModel
import com.example.pal.ui.homeScreens.home.HomeAdapter
import com.example.pal.ui.signin.LoginViewModel
import com.example.pal.util.Loading
import com.example.pal.util.Success
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var binding: FragmentFavoritesBinding by autoCleared()

    private val viewModel: FavoritesViewModel by viewModels()

    // the activity viewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        if (activityViewModel.userStatus) {
            viewModel.getPets(activityViewModel.userFavorites)
        }

        // navigate to the log in screen
        binding.signInFavoriteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_loginFragment2)
        }

        // the bottom menu ref, and set the manu to be visible every time we come back to this screen
        val navigationBar =
            (activity as MainActivity).findViewById<ViewGroup>(R.id.bottom_navigation)
        navigationBar.isVisible = true

        binding.guestScreen.isVisible = !activityViewModel.userStatus
        binding.userScreen.isVisible = activityViewModel.userStatus

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activityViewModel.userStatus) {

            binding.favoritePets.layoutManager = LinearLayoutManager(requireContext())

            binding.favoritePets.adapter = HomeAdapter(object : HomeAdapter.PetsListener {
                override fun onPetClicked(index: Int, petId: Int) {

                    //when pressing on a pet we passing the id of the dog to single pet fragment
                    findNavController().navigate(
                        R.id.action_favoritesFragment_to_singlePet,
                        bundleOf("petId" to petId)
                    )
                }
            })

            viewModel.pets.observe(viewLifecycleOwner) {
                when (it.status) {

                    // when the user status is loading we will show the loading anim ui
                    is Loading -> {
                        binding.userScreen.isVisible = false
                        binding.favoritesLoading.isVisible = true
                    }

                    // when the user status is success we will move to the next page and reset the ui
                    is Success -> {
                        binding.userScreen.isVisible = true
                        binding.favoritesLoading.isVisible = false
                        (binding.favoritePets.adapter as HomeAdapter).setPets(it.status.data!!)
                    }

                    // if the user status is failed we will pop up the message and wont change the ui
                    is Error -> {
                        binding.userScreen.isVisible = true
                        binding.favoritesLoading.isVisible = false
                        Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

}