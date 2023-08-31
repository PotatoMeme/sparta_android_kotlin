package com.team8.applemarket

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team8.applemarket.databinding.ActivityDetailBinding
import com.team8.applemarket.model.Item
import com.team8.applemarket.model.User
import com.team8.applemarket.util.Util.numFormatter

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailActivity"

        const val INTENT_ITEM = "intentItem"
        private const val INTENT_USER = "intentUser"
        const val IS_FAVORITE_CHANGED = "isFavoritChanged"

        fun newIntent(context: Context, item: Item, user: User): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(INTENT_ITEM, item)
                putExtra(INTENT_USER, user)
            }
    }

    private lateinit var binding: ActivityDetailBinding

    private val item by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(INTENT_ITEM, Item::class.java)
        } else {
            intent.getParcelableExtra(INTENT_ITEM)
        }
    }

    private val user by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(INTENT_USER, User::class.java)
        } else {
            intent.getParcelableExtra(INTENT_USER)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        if (item != null && user != null) {
            val item: Item = item!!
            val user: User = user!!
            itemImageView.setImageResource(item.imgRes)
            when (user.menorTemperature.toInt()) {
                in 0 until 20 -> userTemperatureImageView.setImageResource(R.drawable.dead)
                in 20 until 40 -> userTemperatureImageView.setImageResource(R.drawable.sad)
                in 40 until 60 -> userTemperatureImageView.setImageResource(R.drawable.neutral)
                in 60 until 80 -> userTemperatureImageView.setImageResource(R.drawable.shy)
                in 80..100 -> userTemperatureImageView.setImageResource(R.drawable.happy)
            }
            itemNameTextView.text = item.name
            itemDescriptionTextView.text = item.description
            val priceStr = "${item.price.numFormatter()}원"
            itemPriceTextView.text = priceStr

            userNameTextView.text = user.name
            userAddressTextView.text = user.address
            val userTemperature = "${user.menorTemperature}°C"
            userTemperatureTextView.text = userTemperature

            itemFavoriteImageView.isSelected = item.favoriteFlag

            backArrowImageView.setOnClickListener { finish() }

            itemFavoriteImageView.setOnClickListener {
                //todo 좋아요 로직 구현
                itemFavoriteImageView.isSelected = !itemFavoriteImageView.isSelected
                intent.putExtra(IS_FAVORITE_CHANGED,itemFavoriteImageView.isSelected != item.favoriteFlag)
                setResult(RESULT_OK,intent)
            }
        }
    }
}