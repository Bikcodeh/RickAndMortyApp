package com.bikcode.rickandmortyapp.presentation.di

import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepository
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.data.episode.EpisodeRepository
import com.bikcode.rickandmortyapp.presentation.data.episode.EpisodeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { CharacterRepositoryImpl(get(), get()) }
    factory { EpisodeRepositoryImpl(apiService =  get()) }
}