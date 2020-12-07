package com.bikcode.rickandmortyapp.presentation.di

import com.bikcode.rickandmortyapp.presentation.viewmodel.CharacterDetailViewModel
import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CharacterDetailViewModel(episodeRepository =  get()) }
}