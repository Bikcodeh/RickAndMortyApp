package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity

class FavoriteViewModel(private val characterRepository: CharacterRepositoryImpl) :
    ViewModel() {

    val characterList: LiveData<List<CharacterEntity>>
        get() = characterRepository.getCharactersDB()

}