package com.bikcode.rickandmortyapp.presentation.data.character

import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterLocalRepository
import com.bikcode.rickandmortyapp.presentation.database.toCharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterRepositoryImpl(
    private val apiService: APIService,
    private val characterLocalRepository: CharacterLocalRepository
) : CharacterRepository {

    override fun getCharacters(): Single<CharacterResponseServer> {
        return apiService.getCharacters()
    }

    override fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean> {
        return characterLocalRepository.getCharacterById(id)
            .isEmpty
            .flatMapMaybe { isEmpty ->
                Maybe.just(!isEmpty)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun updateFavoriteCharacterStatus(characterEntity: CharacterEntity): Maybe<Boolean> {
        return characterLocalRepository.getCharacterById(characterEntity.id)
            .isEmpty
            .flatMapMaybe { isEmpty ->
                if(isEmpty)
                    characterLocalRepository.insertCharacter(characterEntity)
                else
                    characterLocalRepository.deleteCharacter(characterEntity)
                Maybe.just(isEmpty)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}