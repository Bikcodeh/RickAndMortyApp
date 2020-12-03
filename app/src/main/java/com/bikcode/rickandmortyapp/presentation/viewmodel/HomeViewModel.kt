package com.bikcode.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repositoryImpl: CharacterRepositoryImpl): ViewModel() {

    private val _characterList: MutableLiveData<CharacterState> = MutableLiveData()
    val characterList: LiveData<CharacterState> get() = _characterList

    fun getCharacters() {
        disposable.add(repositoryImpl.getCharacters()
            .doOnSubscribe{
                _characterList.postValue(CharacterState.Loading)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    _characterList.postValue(CharacterState.ShowCharacterList(it.results))
            }, {
                _characterList.postValue(CharacterState.ShowCharacterError(it.message ?: "Error"))
            }))
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    sealed class CharacterState{
        data class ShowCharacterList(val characterList: List<CharacterServer>): CharacterState()
        data class ShowCharacterError(val error: String): CharacterState()
        object Loading: CharacterState()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}