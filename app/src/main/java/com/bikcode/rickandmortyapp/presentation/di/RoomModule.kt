package com.bikcode.rickandmortyapp.presentation.di

import androidx.room.Room
import com.bikcode.rickandmortyapp.presentation.database.CharacterDatabase
import com.bikcode.rickandmortyapp.presentation.database.dao.character.CharacterLocalRepository
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CharacterDatabase::class.java,
            CharacterDatabase.DATABASE_NAME
        ).build()
    }

    single {
        CharacterLocalRepository(
            get()
        )
    }
}