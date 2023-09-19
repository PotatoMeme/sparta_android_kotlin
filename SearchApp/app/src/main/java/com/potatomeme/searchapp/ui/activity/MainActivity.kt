package com.potatomeme.searchapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.searchapp.data.repository.SearchRepositoryImpl
import com.potatomeme.searchapp.data.sharedpreferences.MySharedPreferences
import com.potatomeme.searchapp.ui.adapter.ViewPagerAdapter
import com.potatomeme.searchapp.databinding.ActivityMainBinding
import com.potatomeme.searchapp.ui.viewmodel.MainViewModel
import com.potatomeme.searchapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    private val viewmodel: MainViewModel by viewModels {
        MainViewModelFactory(SearchRepositoryImpl(MySharedPreferences(this)))
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