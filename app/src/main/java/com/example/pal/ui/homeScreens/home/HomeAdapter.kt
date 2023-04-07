package com.example.pal.ui.homeScreens.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pal.data.models.Pet
import com.example.pal.databinding.HomeItemBinding

class HomeAdapter(private val callBack: PetsListener) :
    RecyclerView.Adapter<HomeAdapter.PetViewHolder>() {

    // the pets array list
    private val pets = ArrayList<Pet>()

    // set the pets
    @SuppressLint("NotifyDataSetChanged")
    fun setPets(pets: Collection<Pet>) {
        this.pets.clear()
        this.pets.addAll(pets)
        notifyDataSetChanged()
    }

    // hold the view and bind it to the information with bind fun
    inner class PetViewHolder(private val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // moving the click action forward to onClick fun
        init {
            binding.root.setOnClickListener(this)
        }

        // bind the data to the view
        fun bind(pet: Pet) {

            binding.itemSex.text = pet.sex
            binding.itemAge.text = pet.age
            binding.itemBreed.text = pet.breed
            binding.itemName.text = pet.name

            Glide.with(binding.root).load(pet.pic).into(binding.itemPic)

        }

        // init the onClick fun
        override fun onClick(p0: View?) {

            // move the action out to outside of this class
            // callBack.onPetClicked(adapterPosition)
            if (p0 != null) {
                // click on the recycler we take the position
                // and the id of the pet in the same position
                callBack.onPetClicked(adapterPosition, pets[adapterPosition].id)
            }

        }
    }

    // the 3 functions of the HomeAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder =
        PetViewHolder(
            HomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) =
        // the function that bind the data to the view from the inner class
        holder.bind(pets[position])

    override fun getItemCount() = pets.size

    // listen to the actions on click
    interface PetsListener {
        fun onPetClicked(index: Int, petId: Int)
    }

}