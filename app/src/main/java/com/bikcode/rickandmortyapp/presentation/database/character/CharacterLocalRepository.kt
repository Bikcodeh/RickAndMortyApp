package com.bikcode.rickandmortyapp.presentation.database.character

import androidx.lifecycle.LiveData
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

class CharacterLocalRepository(private val characterDao: CharacterDao):
    CharacterDataSource {

    override fun getAllFavoriteCharacters(): LiveData<List<CharacterEntity>> {
        return characterDao.getAllFavoriteCharacters()
    }

    override fun getCharacterById(id: Int): Maybe<CharacterEntity> {
        return characterDao.getCharacterById(id)
    }

    override fun insertCharacter(characterEntity: CharacterEntity) {
        characterDao.insertCharacter(characterEntity)
    }

    override fun deleteCharacter(characterEntity: CharacterEntity) {
        characterDao.deleteCharacter(characterEntity)
    }
}