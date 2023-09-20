package com.potatomeme.searchapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.SampleItem
import com.potatomeme.searchapp.data.model.toSampleItem
import com.potatomeme.searchapp.data.repository.SearchRepositoryImpl
import com.potatomeme.searchapp.data.sharedpreferences.MySharedPreferences
import com.potatomeme.searchapp.databinding.ActivityWebViewBinding
import com.potatomeme.searchapp.ui.viewmodel.MainViewModel
import com.potatomeme.searchapp.ui.viewmodel.MainViewModelFactory

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WebViewActivity"
        private const val ITEM = "ITEM"
        fun newIntent(
            context: Context,
            item: Item,
        ) = Intent(context, WebViewActivity::class.java).apply {
            putExtra(ITEM, item.toSampleItem())
        }
    }

    private val binding by lazy { ActivityWebViewBinding.inflate(layoutInflater) }
    private val viewmodel: MainViewModel by viewModels {
        MainViewModelFactory(SearchRepositoryImpl(MySharedPreferences(this)))
    }
    private val item by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ITEM, SampleItem::class.java)
        } else {
            intent?.getParcelableExtra(ITEM)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding){
        webView.apply {
            settings.run {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl(item?.link!!)
        }

        backArrowImageView.setOnClickListener { finish() }
        titleTextView.text = item?.title
        favoriteImageView.isSelected = item?.isFavorite!!
        favoriteImageView.setOnClickListener {
            if (favoriteImageView.isSelected){
                viewmodel.removeFavoriteItem(item!!)
            }else{
                viewmodel.addFavoriteItem(item!!.copy(isFavorite = true))
            }
            favoriteImageView.isSelected = !favoriteImageView.isSelected
        }
        //Log.d(TAG, "initViews: ${viewmodel.itemList.value.orEmpty().size} ")
    }


    override fun onBackPressed() { // 뒤로가기 기능 구현
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }
}