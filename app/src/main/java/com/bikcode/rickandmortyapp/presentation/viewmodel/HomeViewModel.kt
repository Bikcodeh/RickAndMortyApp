package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repositoryImpl: CharacterRepositoryImpl) : ViewModel() {

    private val _events = MutableLiveData<Event<CharacterState>>()
    val events: LiveData<Event<CharacterState>> get() = _events

    private var isLoading = false

    fun getAllCharactersDB(callback: (List<CharacterEntity>) -> Unit) {
        viewModelScope.launch {
            val count = repositoryImpl.isEmptyCharacters()
            count.collect {
                if (it > 0) {
                    val characters =
                        repositoryImpl.getAllCharactersDB()
                    characters.collect { characters ->
                        callback.invoke(characters)
                    }
                } else {
                    getCharacters()
                }
            }
        }
    }

    fun getCharacters() {
        disposable.add(repositoryImpl.getCharacters()
            .doOnSubscribe {
                showLoading()
            }
            .subscribe({
                hideLoading()
                _events.value = (Event(CharacterState.ShowCharacterList(it)))
            }, {
                hideLoading()
                _events.value = (
                        Event(
                            CharacterState.ShowCharacterError(
                                it.message ?: "Error"
                            )
                        )
                        )
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