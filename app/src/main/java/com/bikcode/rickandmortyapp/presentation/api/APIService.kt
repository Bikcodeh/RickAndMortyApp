package com.bikcode.rickandmortyapp.presentation.api

import io.reactivex.Single
import retrofit2.http.GET

interface APIService {

    @GET(APIConstants.ENDPOINT_CHARACTER)
    fun getCharacters(): Single<CharacterResponseServer>
}