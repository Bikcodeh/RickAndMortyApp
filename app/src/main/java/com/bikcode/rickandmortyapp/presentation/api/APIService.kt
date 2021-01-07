package com.bikcode.rickandmortyapp.presentation.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET(APIConstants.ENDPOINT_CHARACTER)
    fun getCharacters(): Single<CharacterResponseServer>

    @GET
    fun getEpisode(@Url episodeUrl: String): Single<EpisodeServer>
}