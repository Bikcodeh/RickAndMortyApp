package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.api.EpisodeServer
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.data.episode.EpisodeRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.database.toCharacterEntity
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class CharacterDetailViewModel(
    private val episodeRepository: EpisodeRepositoryImpl,
    private val characterRepository: CharacterRepositoryImpl
) : ViewModel() {

    private val _events: MutableLiveData<Event<CharacterDetailEvent>> = MutableLiveData()
    val events: LiveData<Event<CharacterDetailEvent>> get() = _events

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private var isLoading = false

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun getEpisodes(episodeList: List<String>) {
        disposable.add(
            episodeRepository.getEpisode(episodeList)
                .doOnSubscribe {
                    showLoading()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _events.value = Event(CharacterDetailEvent.ShowListEpisodes(it))
                    hideLoading()
                }, {
                    _events.value = Event(CharacterDetailEvent.ShowErrorList(it.message ?: "Error"))
                    hideLoading()
                })
        )
    }

    fun validateCharacter(characterServer: CharacterServer?) {
        if (characterServer == null) {
            _events.value = Event(CharacterDetailEvent.CloseActivity)
            return
        }
        validateFavoriteCharacterStatus(characterServer.id)
    }

    private fun validateFavoriteCharacterStatus(id: Int) {
        disposable.add(characterRepository.getFavoriteCharacterStatus(id)
            .subscribe { isFavorite ->
                _isFavorite.value = isFavorite
            })
    }

    fun updateFavoriteCharacterStatus(characterServer: CharacterServer) {
        disposable.add(characterRepository.updateFavoriteCharacterStatus(characterServer.toCharacterEntity())
            .subscribe { isFavorite ->
                _isFavorite.value = isFavorite
            })
    }

    private fun showLoading() {
        isLoading = true
        _events.postValue(Event(CharacterDetailEvent.ShowLoadingListEpisodes))
    }

    private fun hideLoading() {
        isLoading = false
        _events.postValue(Event(CharacterDetailEvent.HideLoadingListEpisodes))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class CharacterDetailEvent {
        data class ShowListEpisodes(val list: List<EpisodeServer>) : CharacterDetailEvent()
        data class ShowErrorList(val error: String) : CharacterDetailEvent()
        object ShowLoadingListEpisodes : CharacterDetailEvent()
        object HideLoadingListEpisodes : CharacterDetailEvent()
        object CloseActivity : CharacterDetailEvent()
    }
}