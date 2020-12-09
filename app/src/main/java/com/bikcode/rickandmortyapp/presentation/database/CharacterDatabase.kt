package com.bikcode.rickandmortyapp.presentation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterDao

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(ListStringConverters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "rick_and_morty_db"
    }
}