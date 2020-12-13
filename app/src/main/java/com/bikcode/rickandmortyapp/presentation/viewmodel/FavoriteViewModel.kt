package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterLocalRepository

class FavoriteViewModel(private val characterLocalRepository: CharacterLocalRepository) :
    ViewModel() {

    val characterList: LiveData<List<CharacterEntity>> get() = characterLocalRepository.getAllFavoriteCharacters()

}