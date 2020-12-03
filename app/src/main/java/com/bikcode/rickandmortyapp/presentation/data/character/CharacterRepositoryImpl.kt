package com.bikcode.rickandmortyapp.presentation.data.character

import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import io.reactivex.Single

class CharacterRepositoryImpl(private val apiService: APIService): CharacterRepository {

    override fun getCharacters(): Single<CharacterResponseServer> {
        return apiService.getCharacters()
    }
}