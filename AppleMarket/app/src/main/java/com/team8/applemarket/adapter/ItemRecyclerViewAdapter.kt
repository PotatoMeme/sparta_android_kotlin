package com.team8.applemarket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team8.applemarket.R
import com.team8.applemarket.SampleData
import com.team8.applemarket.databinding.ItemRecyclerViewBinding
import com.team8.applemarket.model.Item
import com.team8.applemarket.model.User
import com.team8.applemarket.util.Util.numFormatter
import kotlin.math.log

class ItemRecyclerViewAdapter(
    defaultItemArray: Array<Item>,
    val clickEventLister: ClickEventLister,
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    interface ClickEventLister {
        fun onClickItemListener(item: Item, user: User)
        fun onLongClickItemListener(function: () -> (Unit))
    }

    val itemList: ArrayList<Item> = arrayListOf<Item>().apply {
        addAll(defaultItemArray)
    }

    fun itemFavoriteChanged(id: Int) {
        val itemIndex = itemList.indexOfFirst { it.id == id }
        val currentItem: Item = itemList[itemIndex]
        val changedItem = if (currentItem.favoriteFlag) {
            currentItem.copy(favoriteFlag = false, favoriteCount = currentItem.favoriteCount - 1)
        } else {
            currentItem.copy(favoriteFlag = true, favoriteCount = currentItem.favoriteCount + 1)
        }
        itemList[itemIndex] = changedItem
        notifyItemChanged(itemIndex)
    }

    inner class ViewHolder(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) = with(binding) {
            val currentItem = itemList[pos]

            itemImageView.setImageResource(currentItem.imgRes)
            if (currentItem.favoriteFlag) itemFavoriteImageView.setImageResource(R.drawable.fill_heart)

            itemNameTextView.text = currentItem.name
            itemPriceTextView.text = "${currentItem.price.numFormatter()}원"
            itemTalkCountTextView.text = currentItem.talkCount.numFormatter()
            itemFavoriteCountTextView.text = (currentItem.favoriteCount).numFormatter()

            val currentUser: User? = SampleData.userArr.find { it.id == currentItem.userId }
            userAddressTextView.text = currentUser?.address

            root.setOnClickListener {
                clickEventLister.onClickItemListener(
                    currentItem,
                    currentUser!!
                )
            }
            root.setOnLongClickListener {
                clickEventLister.onLongClickItemListener {
                    val itemIndex: Int = itemList.indexOfFirst { it.id == currentItem.id }
                    itemList.removeAt(itemIndex)
                    notifyItemRangeRemoved(itemIndex, 1)
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}