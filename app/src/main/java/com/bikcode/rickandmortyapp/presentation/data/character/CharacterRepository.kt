package com.bikcode.rickandmortyapp.presentation.data.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun isEmptyCharacters(): Flow<Int>
    fun getAllCharactersDB(): Flow<List<CharacterEntity>>
    fun insertCharacters(characters: List<CharacterEntity>)
    fun getCharacters(): Single<List<Character>>
    fun getCharactersDB(): LiveData<List<CharacterEntity>>
    fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean>
    suspend fun updateFavoriteCharacterStatus(characterEntity: CharacterEntity): Int
}