package com.bikcode.rickandmortyapp.presentation.data.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import com.bikcode.rickandmortyapp.presentation.api.toCharacterDomainList
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterDao
import com.bikcode.rickandmortyapp.presentation.database.toCharacterEntity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val apiService: APIService,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override suspend fun isEmptyCharacters(): Int {
        return characterDao.countCharacters()
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

    override suspend fun getAllCharactersDB(): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    override fun getCharactersDB(): LiveData<List<CharacterEntity>> =
        characterDao.getAllFavoriteCharacters()

    override fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean> {
        return characterDao.getCharacterById(id)
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