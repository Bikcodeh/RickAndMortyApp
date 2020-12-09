package com.bikcode.rickandmortyapp.presentation.database

import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.api.LocationServer
import com.bikcode.rickandmortyapp.presentation.api.OriginServer

fun CharacterServer.toCharacterEntity() = CharacterEntity(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginEntity(),
    location.toLocationEntity(),
    episodeList
)

fun OriginServer.toOriginEntity() = OriginEntity(
    name,
    url
)

fun LocationServer.toLocationEntity() = LocationEntity(
    name,
    url
)