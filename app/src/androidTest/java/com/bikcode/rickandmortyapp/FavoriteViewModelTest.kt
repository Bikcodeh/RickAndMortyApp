package com.bikcode.rickandmortyapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bikcode.rickandmortyapp.presentation.database.CharacterDatabase
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.LocationEntity
import com.bikcode.rickandmortyapp.presentation.database.OriginEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteViewModelTest {
    private lateinit var characterDao: CharacterDao
    private lateinit var db: CharacterDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java
        ).build()
        characterDao = db.characterDao()
    }

    @Test
    fun characterListShouldReturnACharacterEntityList() {

        characterDao.insertCharacter(
            CharacterEntity(
                1, "", "",
                "", "", "", OriginEntity("", ""), LocationEntity("", ""), emptyList()
            )
        )

        val data = characterDao.getAllFavoriteCharacters()
        Assert.assertNotNull(data)
    }

    @Test
    fun characterDaoShouldReturnACharacterEntityList() {
            val character = CharacterEntity(
                1, "", "",
                "", "", "", OriginEntity("", ""), LocationEntity("", ""), emptyList()
            )

        val characterB = character.copy(id = 2)

        characterDao.insertCharacter(character)
        characterDao.insertCharacter(characterB)
    }
}










































