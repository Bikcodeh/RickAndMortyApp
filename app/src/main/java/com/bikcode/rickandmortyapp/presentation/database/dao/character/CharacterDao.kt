package com.bikcode.rickandmortyapp.presentation.database.dao.character

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

interface CharacterDao {

    @Query("SELECT * FROM Character")
    fun getAllFavoriteCharacters(): Flowable<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE character_id = :id")
    fun getCharacterById(id: Int): Maybe<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)

    @Delete
    fun deleteCharacter(characterEntity: CharacterEntity)
}