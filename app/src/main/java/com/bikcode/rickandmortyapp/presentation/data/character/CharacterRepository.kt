package com.bikcode.rickandmortyapp.presentation.data.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single

interface CharacterRepository {
    suspend fun isEmptyCharacters(): Int
    fun insertCharacters(characters: List<CharacterEntity>)
    fun getCharacters(): Single<List<Character>>
    fun getAllCharactersDB(): LiveData<List<CharacterEntity>>
    fun getCharactersDB(): LiveData<List<CharacterEntity>>
    fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean>
    suspend fun updateFavoriteCharacterStatus(characterEntity: CharacterEntity): Int
}