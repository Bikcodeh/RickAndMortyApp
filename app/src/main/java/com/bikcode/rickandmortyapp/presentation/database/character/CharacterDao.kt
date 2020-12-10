package com.bikcode.rickandmortyapp.presentation.database.character

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character")
    fun getAllFavoriteCharacters(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE character_id = :id")
    fun getCharacterById(id: Int): Maybe<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)

    @Delete
    fun deleteCharacter(characterEntity: CharacterEntity)
}