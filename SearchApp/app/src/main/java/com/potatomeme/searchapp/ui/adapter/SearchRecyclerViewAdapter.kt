package com.potatomeme.searchapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.searchapp.databinding.ItemSearchBinding
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.image.ImageDocument

class SearchRecyclerViewAdapter(val listener: EventListener) :
    ListAdapter<Item, SearchRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {

    interface EventListener {

        fun onClickEventListener(item: Item)

        fun onFavoritImageClicked(item: Item)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.imgUrl == newItem.imgUrl
            }

        }
    }


    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = with(binding) {
            root.setOnClickListener {
                listener.onClickEventListener(item)
            }

            Glide.with(root)
                .load(item.imgUrl)
                .into(itemImgaeView)

            titleTextView.text = item.title
            dateTextView.text = item.date

            favoriteImageView.isSelected = item.isFavorite

            if (item is ImageDocument) playImageView.visibility = View.GONE
            else playImageView.visibility = View.VISIBLE

            favoriteImageView.setOnClickListener {
                favoriteImageView.isSelected = !favoriteImageView.isSelected
                item.isFavorite = favoriteImageView.isSelected
                listener.onFavoritImageClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}