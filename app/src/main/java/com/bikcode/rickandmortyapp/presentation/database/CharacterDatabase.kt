package com.bikcode.rickandmortyapp.presentation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverters::class)
abstract class CharacterDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "character_db"
    }
}