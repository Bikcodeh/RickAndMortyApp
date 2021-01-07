package com.bikcode.rickandmortyapp.presentation.data.episode

import com.bikcode.rickandmortyapp.presentation.api.EpisodeServer
import io.reactivex.Single

interface EpisodeRepository {
    fun getEpisode(episodeUrlList: List<String>): Single<List<EpisodeServer>>
}