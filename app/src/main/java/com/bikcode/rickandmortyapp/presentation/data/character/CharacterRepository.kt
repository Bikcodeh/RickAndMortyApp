package com.bikcode.rickandmortyapp.presentation.data.character

import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacters(): Single<List<Character>>
    fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean>
    fun updateFavoriteCharacterStatus(characterEntity: CharacterEntity): Maybe<Boolean>
}