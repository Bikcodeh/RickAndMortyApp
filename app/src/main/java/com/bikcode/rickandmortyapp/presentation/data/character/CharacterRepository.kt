package com.bikcode.rickandmortyapp.presentation.data.character

import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacters(): Single<CharacterResponseServer>
}