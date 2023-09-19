package com.potatomeme.searchapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.ui.adapter.SearchRecyclerViewAdapter
import com.potatomeme.searchapp.databinding.FragmentStoreBinding
import com.potatomeme.searchapp.data.model.SampleItem
import com.potatomeme.searchapp.ui.viewmodel.MainViewModel

class StoreFragment : Fragment() {

    companion object {
        private const val TAG = "StoreFragment"
        fun newInstance() = StoreFragment()
    }

    private var _binding: FragmentStoreBinding? = null
    private val binding: FragmentStoreBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val recyclerViewAdapter: SearchRecyclerViewAdapter by lazy {
        SearchRecyclerViewAdapter(object : SearchRecyclerViewAdapter.EventListener {
            override fun onClickEventListener(item: Item) {
                Log.d(TAG, "onClickEventListener: ")
                // todo webview로 가서 동작할수 있도록
            }

            override fun onFavoritImageClicked(item: Item) {
                Log.d(TAG, "onFavoritImageClicked: ")
                // todo 좋아요 버튼 동작
                if (item.isFavorite){
                    viewModel.addFavoriteItem(item)
                }else{
                    viewModel.removeFavoriteItem(item)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        recyclerView.apply {
            adapter = recyclerViewAdapter

            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            layoutManager = staggeredGridLayoutManager
        }

        viewModel.favoriteItemList.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it.toList())
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}