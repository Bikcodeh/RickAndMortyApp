package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterLocalRepository
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val characterLocalRepository: CharacterLocalRepository) :
    ViewModel() {

    private val _events: MutableLiveData<Event<FavoriteCharactersNavigation>> = MutableLiveData()
    val events: LiveData<Event<FavoriteCharactersNavigation>> get() = _events

    val characterList: LiveData<List<CharacterEntity>> get() = characterLocalRepository.getAllFavoriteCharacters()

    private var isLoading = false

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun getFavoriteCharacters() {
        /*disposable.add(characterLocalRepository.getAllFavoriteCharacters()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                showLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listCharacters ->
                _events.value =
                    Event(FavoriteCharactersNavigation.ShowFavoriteCharacters(listCharacters))
                hideLoading()
            }, { error ->
                _events.value = Event(
                    FavoriteCharactersNavigation.ShowError(
                        error.message ?: "Error loading list"
                    )
                )
                hideLoading()
            })
        )*/
    }

    private fun showLoading() {
        isLoading = true
        _events.postValue(Event(FavoriteCharactersNavigation.ShowProgressFavoriteCharacters))
    }

    private fun hideLoading() {
        isLoading = false
        _events.postValue(Event(FavoriteCharactersNavigation.HideProgressFavoriteCharacters))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class FavoriteCharactersNavigation {
        data class ShowFavoriteCharacters(val favoriteList: List<CharacterEntity>) :
            FavoriteCharactersNavigation()
        data class ShowError(val message: String) : FavoriteCharactersNavigation()
        object ShowProgressFavoriteCharacters : FavoriteCharactersNavigation()
        object HideProgressFavoriteCharacters : FavoriteCharactersNavigation()
    }
}