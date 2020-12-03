package com.bikcode.rickandmortyapp.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.ui.adapter.CharacterListAdapter
import com.bikcode.rickandmortyapp.presentation.util.showLongToast
import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment: Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        init()
        handle()
        homeViewModel.getCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun init() {
        characterListAdapter = CharacterListAdapter()
    }

    private fun handle() {
        homeViewModel.characterList.observe(this, Observer { state ->
            when(state) {
                is HomeViewModel.CharacterState.ShowCharacterList -> {
                    characterListAdapter.setData(state.characterList)
                    home_rv_characters.apply {
                        layoutManager = GridLayoutManager(context, 2)
                        adapter = characterListAdapter
                    }
                    home_progress.visibility = View.GONE
                }
                is HomeViewModel.CharacterState.ShowCharacterError -> {
                    context?.showLongToast(state.error)
                    home_progress.visibility = View.GONE
                }
                HomeViewModel.CharacterState.Loading -> {
                    home_progress.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {

        const val TAG = "HOME_FRAGMENT"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}