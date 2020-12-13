package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val repositoryImpl: CharacterRepositoryImpl) : ViewModel() {

    private val _events = MutableLiveData<Event<CharacterState>>()
    val events: LiveData<Event<CharacterState>> get() = _events

    private var isLoading = false

    fun getCharacters() {
        disposable.add(repositoryImpl.getCharacters()
            .doOnSubscribe {
                showLoading()
            }
            .subscribe({
                hideLoading()
                _events.postValue(Event(CharacterState.ShowCharacterList(it)))
            }, {
                hideLoading()
                _events.postValue(Event(CharacterState.ShowCharacterError(it.message ?: "Error")))
            })
        )
    }

    private fun showLoading() {
        isLoading = true
        _events.value = Event(CharacterState.ShowLoading)
    }

    private fun hideLoading() {
        isLoading = false
        _events.value = Event(CharacterState.HideLoading)
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    sealed class CharacterState {
        data class ShowCharacterList(val characterList: List<Character>) : CharacterState()
        data class ShowCharacterError(val error: String) : CharacterState()
        object ShowLoading : CharacterState()
        object HideLoading : CharacterState()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}