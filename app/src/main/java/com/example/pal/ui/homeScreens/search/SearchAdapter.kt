package com.example.pal.ui.homeScreens.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pal.data.models.Cat
import com.example.pal.data.models.Dog
import com.example.pal.databinding.SearchItemBinding

class SearchAdapter(
    private val callBackDog: DogsListener?, private val callBackCat: CatsListener?,
    private val petType: String
) : RecyclerView.Adapter<SearchAdapter.PetViewHolder>() {

    private val dogs = ArrayList<Dog>()
    private val cats = ArrayList<Cat>()

    // set the dogs
    @SuppressLint("NotifyDataSetChanged")
    fun setDogs(dogs: Collection<Dog>) {
        this.dogs.clear()
        this.dogs.addAll(dogs)
        notifyDataSetChanged()
    }

    // set the cats
    @SuppressLint("NotifyDataSetChanged")
    fun setCats(cats: Collection<Cat>) {
        this.cats.clear()
        this.cats.addAll(cats)
        notifyDataSetChanged()
    }

    // hold the view and bind it to the information with bind fun
    inner class PetViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // moving the click action forward to onClick fun
        init {
            binding.root.setOnClickListener(this)
        }

        // bind the dog data to the view
        fun bindDog(dog: Dog) {

            binding.itemBreed.text = dog.Breed
            binding.itemWeight.text = dog.Weight
            binding.itemLifeSpan.text = dog.Life_Span

            Glide.with(binding.root).load(dog.image).into(binding.itemPic)

        }

        // bind the cat data to the view
        @SuppressLint("SetTextI18n")
        fun bindCat(cat: Cat) {
            binding.itemBreed.text = cat.name
            binding.itemWeight.text = ((cat.max_weight + cat.min_weight) / 2).toString()
            binding.itemLifeSpan.text =
                ((cat.max_life_expectancy + cat.min_life_expectancy) / 2).toString()

            Glide.with(binding.root).load(cat.image_link).into(binding.itemPic)

        }

        // init the onClick fun
        override fun onClick(p0: View?) {
            if (p0 != null && petType == "Dog") {
                // move the action out to outside of this class
                callBackDog?.onPetClicked(adapterPosition, dogs[adapterPosition].Breed)
            } else if (p0 != null && petType == "Cat") {
                // move the action out to outside of this class
                callBackCat?.onPetClicked(adapterPosition, cats[adapterPosition].name)
            }
        }
    }

    // the 3 functions of the HomeAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder =
        PetViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) =
        // the function that bind the data to the view from the inner class
        if (petType == "Dog") {
            holder.bindDog(dogs[position])
        } else {
            holder.bindCat(cats[position])
        }


    override fun getItemCount() =
        if (petType == "Dog") {
            dogs.size
        } else {
            cats.size
        }

    // listen to the actions on click
    interface DogsListener {
        fun onPetClicked(index: Int, breed: String)
    }

    // listen to the actions on click
    interface CatsListener {
        fun onPetClicked(index: Int, breed: String)
    }

}