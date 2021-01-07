package com.bikcode.rickandmortyapp.presentation.database.character

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import io.reactivex.Maybe

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT COUNT(character_id) FROM character")
    suspend fun countCharacters(): Int

    @Query("SELECT * FROM character")
    fun getAllCharacters(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE status_favorite = 1")
    fun getAllFavoriteCharacters(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE character_id = :id")
    fun getCharacterById(id: Int): Maybe<CharacterEntity>

    @Query("SELECT status_favorite FROM Character WHERE character_id = :id")
    suspend fun getCharacterStatusById(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStatus(characterEntity: CharacterEntity): Int

    @Delete
    fun deleteCharacter(characterEntity: CharacterEntity)
}