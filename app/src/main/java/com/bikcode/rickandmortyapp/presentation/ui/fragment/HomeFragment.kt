package com.bikcode.rickandmortyapp.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.interfaces.CharacterCallback
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.toCharacter
import com.bikcode.rickandmortyapp.presentation.parcelables.toCharacterParcelable
import com.bikcode.rickandmortyapp.presentation.ui.activity.CharacterDetailActivity
import com.bikcode.rickandmortyapp.presentation.ui.adapter.CharacterListAdapter
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import com.bikcode.rickandmortyapp.presentation.util.showLongToast
import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), CharacterCallback {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        homeViewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))

        homeViewModel.data.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty())
                characterListAdapter.setData(it.map { itemList -> itemList.toCharacter() })
            else
                homeViewModel.getCharacters()
        })
    }

    private fun init() {
        characterListAdapter = CharacterListAdapter(this)
        home_rv_characters.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = characterListAdapter
        }
    }

    private fun validateEvents(event: Event<HomeViewModel.CharacterState>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when(navigation){
                is HomeViewModel.CharacterState.ShowCharacterList -> navigation.run {
                    characterListAdapter.setData(navigation.characterList)
                    home_progress.visibility = View.GONE
                }
                is HomeViewModel.CharacterState.ShowCharacterError -> navigation.run {
                    context?.showLongToast(navigation.error)
                    home_progress.visibility = View.GONE
                }
                HomeViewModel.CharacterState.ShowLoading -> home_progress.visibility = View.VISIBLE
                HomeViewModel.CharacterState.HideLoading -> home_progress.visibility = View.GONE
            }
        }
    }

    companion object {

        const val TAG = "HOME_FRAGMENT"

        @JvmStatic
        fun newInstance(args: Bundle? = Bundle()) =
            HomeFragment().apply {
                arguments = args
            }
    }

    override fun onCharacterClick(pos: Int, characterImage: ImageView) {
        val intent = Intent(context, CharacterDetailActivity::class.java).apply {
            putExtra("user", characterListAdapter.getCharacterItem(pos).toCharacterParcelable())
        }
        startActivity(intent)
    }
}