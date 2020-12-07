package com.bikcode.rickandmortyapp.presentation.data.episode

import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.EpisodeServer
import com.bikcode.rickandmortyapp.presentation.api.RetrofitClient
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EpisodeRepositoryImpl(private val apiService: APIService): EpisodeRepository {

    override fun getEpisode(episodeUrlList: List<String>): Single<List<EpisodeServer>> {
        return Observable.fromIterable(episodeUrlList)
            .flatMap { episode ->
                apiService.getEpisode(episode)
                    .toObservable()
            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}