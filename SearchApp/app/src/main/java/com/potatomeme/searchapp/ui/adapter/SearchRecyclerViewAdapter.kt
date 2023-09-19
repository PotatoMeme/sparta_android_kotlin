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
import com.potatomeme.searchapp.data.model.image.Document
import com.potatomeme.searchapp.data.model.image.ImageResponse

class SearchRecyclerViewAdapter(val listener : EventListener) : ListAdapter<Item, SearchRecyclerViewAdapter.ViewHolder>(
    diffUtil
) {

    interface EventListener{

        fun onClickEventListener()

        fun onFavoritImageClicked()
    }

    companion object{
        private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }


    inner class ViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item) = with(binding){
            root.setOnClickListener {
                listener.onClickEventListener()
            }

            Glide.with(root)
                .load(item.imgUrl)
                .into(itemImgaeView)

            titleTextView.text = item.title
            dateTextView.text = item.date

            if (item is Document) playImageView.visibility = View.GONE

            favoriteImageView.setOnClickListener {
                favoriteImageView.isSelected = !favoriteImageView.isSelected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}