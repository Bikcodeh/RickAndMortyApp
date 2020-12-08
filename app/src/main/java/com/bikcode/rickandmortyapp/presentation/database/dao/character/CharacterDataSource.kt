package com.bikcode.rickandmortyapp.presentation.database.dao.character

import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

interface CharacterDataSource {
    fun getAllFavoriteCharacters(): Flowable<List<CharacterEntity>>
    fun getCharacterById(id: Int): Maybe<CharacterEntity>
    fun insertCharacter(characterEntity: CharacterEntity)
    fun deleteCharacter(characterEntity: CharacterEntity)
}