package com.bikcode.rickandmortyapp.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        favoriteViewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        favoriteViewModel.characterList.observe(viewLifecycleOwner, Observer {
            favoriteListAdapter.setData(it)
            favorite_rv_characters.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = favoriteListAdapter
            }
        })
        //favoriteViewModel.getFavoriteCharacters()
    }

    private fun validateEvents(event: Event<FavoriteViewModel.FavoriteCharactersNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when(navigation) {
                is FavoriteViewModel.FavoriteCharactersNavigation.ShowFavoriteCharacters -> {
                    favoriteListAdapter.setData(navigation.favoriteList)
                    favorite_rv_characters.apply {
                        layoutManager = GridLayoutManager(context, 2)
                        adapter = favoriteListAdapter
                    }
                }
                is FavoriteViewModel.FavoriteCharactersNavigation.ShowError -> context?.showLongToast(getString(R.string.error_favorite_characters))
                FavoriteViewModel.FavoriteCharactersNavigation.ShowProgressFavoriteCharacters -> favorite_pb_progress.visibility = View.VISIBLE
                FavoriteViewModel.FavoriteCharactersNavigation.HideProgressFavoriteCharacters -> favorite_pb_progress.visibility = View.GONE
            }
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