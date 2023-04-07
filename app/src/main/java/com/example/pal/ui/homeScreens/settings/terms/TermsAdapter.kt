package com.example.pal.ui.homeScreens.settings.terms

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pal.databinding.TermsItemLayoutBinding

class TermsAdapter(val items:List<TermsItem>) : RecyclerView.Adapter<TermsAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: TermsItemLayoutBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(item:TermsItem) {
            binding.itemTitleTerms.text = item.title
            binding.itemDescriptionTerms.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder((TermsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}