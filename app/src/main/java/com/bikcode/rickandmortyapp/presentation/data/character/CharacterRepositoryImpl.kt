package com.bikcode.rickandmortyapp.presentation.data.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.toCharacterDomainList
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterDao
import com.bikcode.rickandmortyapp.presentation.database.toCharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(
    private val apiService: APIService,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override fun isEmptyCharacters(): Flow<Int> = flow {
        characterDao.countCharacters().collect { emit(it) }
    }

    override fun getAllCharactersDB(): Flow<List<CharacterEntity>> = flow {
        characterDao.getAllCharacters().collect { emit(it) }
    }

    override fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }

    override fun getCharacters(): Single<List<Character>> {
        return apiService.getCharacters()
            .flatMap {
                characterDao.insertCharacters(it.results.map { characterServer -> characterServer.toCharacterEntity() })
                Single.just(it.toCharacterDomainList())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun getCharactersDB(): LiveData<List<CharacterEntity>> =
        characterDao.getAllFavoriteCharacters()

    /**
     * Quizaá esta logica se deba cambiar
     */
    override fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean> {
        return characterDao.isCharacterFavorite(id)
            .isEmpty
            .flatMapMaybe { isEmpty ->
                Maybe.just(!isEmpty)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override suspend fun updateFavoriteCharacterStatus(characterEntity: CharacterEntity): Int {
        return characterDao.updateStatus(characterEntity)
    }
}