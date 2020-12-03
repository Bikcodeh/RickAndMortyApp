package com.bikcode.rickandmortyapp.presentation.di

import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { CharacterRepositoryImpl(get()) }
}