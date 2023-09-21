package com.potatomeme.searchapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.searchapp.data.model.SampleItem
import com.potatomeme.searchapp.data.model.toSampleItem
import com.potatomeme.searchapp.data.repository.SearchRepositoryImpl
import com.potatomeme.searchapp.data.sharedpreferences.MySharedPreferences
import com.potatomeme.searchapp.ui.adapter.ViewPagerAdapter
import com.potatomeme.searchapp.databinding.ActivityMainBinding
import com.potatomeme.searchapp.ui.viewmodel.MainViewModel
import com.potatomeme.searchapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    private val viewmodel: MainViewModel by viewModels() {
        MainViewModelFactory(SearchRepositoryImpl(MySharedPreferences(this)))
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK
                && result.data?.getBooleanExtra(WebViewActivity.FAVORITE_CHANGED, false) == true
            ) {
                val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(WebViewActivity.ITEM, SampleItem::class.java)!!
                } else {
                    result.data?.getParcelableExtra(WebViewActivity.ITEM)!!
                }

                if (item.isFavorite){ // 이전값이 true 이니까 이제는  false
                    viewmodel.removeFavoriteItem(item.toSampleItem().copy(isFavorite = false))
                } else {
                    viewmodel.addFavoriteItem(item.toSampleItem().copy(isFavorite = true))
                }
            }
        }

    fun useActivityResultLauncher(intent: Intent) {
        activityResultLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        mainViewpager.adapter = viewPagerAdapter
        TabLayoutMediator(mainTab, mainViewpager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()
    }
}