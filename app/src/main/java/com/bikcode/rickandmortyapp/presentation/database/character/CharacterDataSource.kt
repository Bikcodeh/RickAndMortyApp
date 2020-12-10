package com.bikcode.rickandmortyapp.presentation.database.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe

interface CharacterDataSource {
    fun getAllFavoriteCharacters(): LiveData<List<CharacterEntity>>
    fun getCharacterById(id: Int): Maybe<CharacterEntity>
    fun insertCharacter(characterEntity: CharacterEntity)
    fun deleteCharacter(characterEntity: CharacterEntity)
}