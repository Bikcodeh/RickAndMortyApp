package com.bikcode.rickandmortyapp.presentation.parcelables

import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.api.LocationServer
import com.bikcode.rickandmortyapp.presentation.api.OriginServer
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.data.Location
import com.bikcode.rickandmortyapp.presentation.data.Origin

fun Character.toCharacterParcelable() = CharacterParcelable(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginParcelable(),
    location.toLocationParcelable(),
    episodeList
)

fun Location.toLocationParcelable() = LocationParcelable(
    name,
    url
)

fun Origin.toOriginParcelable() = OriginParcelable(
    name,
    url
)

fun CharacterParcelable.toCharacterServer() = CharacterServer(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginServer(),
    location.toLocationServer(),
    episodeList
)

fun LocationParcelable.toLocationServer() = LocationServer(
    name,
    url
)

fun OriginParcelable.toOriginServer() = OriginServer(
    name,
    url
)
