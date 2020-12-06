package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repositoryImpl: CharacterRepositoryImpl) : ViewModel() {

    private val _events = MutableLiveData<Event<CharacterState>>()
    val events: LiveData<Event<CharacterState>> get() = _events

    private var isLoading = false

    fun getCharacters() {
        disposable.add(repositoryImpl.getCharacters()
            .doOnSubscribe {
                showLoading()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
                _events.postValue(Event(CharacterState.ShowCharacterList(it.results)))
            }, {
                hideLoading()
                _events.postValue(Event(CharacterState.ShowCharacterError(it.message?: "Error")))
            })
        )
    }

    private fun showLoading() {
        isLoading = true
        _events.postValue(Event(CharacterState.ShowLoading))
    }

    private fun hideLoading() {
        isLoading = false
        _events.postValue(Event(CharacterState.HideLoading))
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    sealed class CharacterState {
        data class ShowCharacterList(val characterList: List<CharacterServer>) : CharacterState()
        data class ShowCharacterError(val error: String) : CharacterState()
        object ShowLoading : CharacterState()
        object HideLoading : CharacterState()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}