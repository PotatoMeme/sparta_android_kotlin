package com.potatomeme.searchapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.searchapp.databinding.ItemSearchBinding
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.SampleItem
import com.potatomeme.searchapp.data.model.image.ImageDocument
import com.potatomeme.searchapp.data.model.toSampleItem
import kotlin.math.log

class SearchRecyclerViewAdapter(val listener: EventListener) :
    ListAdapter<Item, SearchRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {

    interface EventListener {

        fun onClickEventListener(item: Item)

        fun onFavoritImageClicked(item: Item)
    }

    companion object {
        private const val TAG = "SearchRecyclerViewAdapt"
        private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.imgUrl == newItem.imgUrl
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.imgUrl == newItem.imgUrl && oldItem.isFavorite == newItem.isFavorite
            }

        }
    }


    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = with(binding) {
            root.setOnClickListener {
                listener.onClickEventListener(item)
            }

            Log.d(TAG, "bind: ${item}")
            Glide.with(root)
                .load(item.imgUrl)
                .into(itemImgaeView)

            titleTextView.text = item.title
            dateTextView.text = item.date

            favoriteImageView.isSelected = item.isFavorite

            if (item.isImage) {
                playImageView.visibility = View.GONE
            } else {
                playImageView.visibility = View.VISIBLE
            }

            favoriteImageView.setOnClickListener {
                favoriteImageView.isSelected = !favoriteImageView.isSelected
                //item.isFavorite = favoriteImageView.isSelected
                listener.onFavoritImageClicked(item.toSampleItem().copy(isFavorite = favoriteImageView.isSelected))
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