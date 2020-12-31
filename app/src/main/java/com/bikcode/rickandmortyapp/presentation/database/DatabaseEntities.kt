package com.bikcode.rickandmortyapp.presentation.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.data.Location
import com.bikcode.rickandmortyapp.presentation.data.Origin


@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "character_id") var id: Int,
    @ColumnInfo(name = "character_name") var name: String,
    @ColumnInfo(name = "character_image") var image: String?,
    @ColumnInfo(name = "character_gender") var gender: String,
    @ColumnInfo(name = "character_species") var species: String,
    @ColumnInfo(name = "character_status") var status: String,
    @Embedded var origin: OriginEntity,
    @Embedded var location: LocationEntity,
    @ColumnInfo(name = "character_episode_list") var episodeList: List<String>,
    @ColumnInfo(name = "status_favorite") var statusFavorite: Boolean = false
)

data class LocationEntity(
    var locationName: String,
    var locationUrl: String
)

data class OriginEntity(
    var originName: String,
    var originUrl: String
)

fun CharacterEntity.toCharacter() = Character(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOrigin(),
    location.toLocation(),
    episodeList,
    statusFavorite
)

fun OriginEntity.toOrigin() = Origin(
    originName,
    originUrl
)

fun LocationEntity.toLocation() = Location(
    locationName,
    locationUrl
)
