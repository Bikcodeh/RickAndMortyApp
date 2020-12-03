package com.bikcode.rickandmortyapp.presentation.di

import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }
}