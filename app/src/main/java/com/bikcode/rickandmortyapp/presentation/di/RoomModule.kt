package com.bikcode.rickandmortyapp.presentation.di

import androidx.room.Room
import com.bikcode.rickandmortyapp.presentation.database.CharacterDatabase
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(get(), CharacterDatabase::class.java, CharacterDatabase.DATABASE_NAME).build() }

    factory { get<CharacterDatabase>().characterDao() }
}