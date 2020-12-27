package com.bikcode.rickandmortyapp.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.parcelables.toCharacterParcelable
import com.bikcode.rickandmortyapp.presentation.ui.activity.CharacterDetailActivity
import com.bikcode.rickandmortyapp.presentation.ui.adapter.CharacterListAdapter
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import com.bikcode.rickandmortyapp.presentation.util.showLongToast
import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
        homeViewModel.getCharacters()
    }

    private fun initObserver() {
        homeViewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
    }

    private fun init() {
        characterListAdapter = CharacterListAdapter { position, image ->
            onCharacterClick(position, image)
        }
    }

    private fun validateEvents(event: Event<HomeViewModel.CharacterState>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is HomeViewModel.CharacterState.ShowCharacterList -> navigation.run {
                    characterListAdapter.setData(navigation.characterList)
                    home_rv_characters.apply {
                        layoutManager = GridLayoutManager(context, 2)
                        adapter = characterListAdapter
                    }
                    home_progress.visibility = View.GONE
                }
                is HomeViewModel.CharacterState.ShowCharacterError -> navigation.run {
                    context?.showLongToast(navigation.error)
                    home_progress.visibility = View.GONE
                }
                HomeViewModel.CharacterState.ShowLoading -> home_progress.visible()
                HomeViewModel.CharacterState.HideLoading -> home_progress.gone()
            }
        }
    }

    private fun View.visible() {
        this.isVisible = true
    }

    private fun View.gone() {
        this.isVisible = false
    }


    companion object {

        const val TAG = "HOME_FRAGMENT"

        @JvmStatic
        fun newInstance(args: Bundle? = Bundle()) =
            HomeFragment().apply {
                arguments = args
            }
    }

    private fun onCharacterClick(pos: Int, characterImage: ImageView) {
        val intent = Intent(context, CharacterDetailActivity::class.java).apply {
            putExtra(
                CharacterDetailActivity.INFO_USER,
                characterListAdapter.getCharacterItem(pos).toCharacterParcelable()
            )
        }
        startActivity(intent)
    }
}