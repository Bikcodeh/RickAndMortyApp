package com.bikcode.rickandmortyapp.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.ui.adapter.FavoriteListAdapter
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import com.bikcode.rickandmortyapp.presentation.util.showLongToast
import com.bikcode.rickandmortyapp.presentation.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val favoriteListAdapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        favoriteViewModel.characterList.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                favoriteListAdapter.setData(it)
                favorite_tv_empty_message.isVisible = false
            } else {
                favoriteListAdapter.clearData()
                favorite_tv_empty_message.isVisible = true
            }
        })
    }

    private fun initView() {
        favorite_rv_characters.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = favoriteListAdapter
        }
    }

    companion object {

        const val TAG = "FAVORITE_FRAGMENT"

        @JvmStatic
        fun newInstance(args: Bundle? = Bundle()) =
            FavoriteFragment()
                .apply {
                arguments = args
            }
    }
}