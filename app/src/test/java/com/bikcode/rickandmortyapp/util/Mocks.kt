package com.bikcode.rickandmortyapp.util

import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.api.LocationServer
import com.bikcode.rickandmortyapp.presentation.api.OriginServer
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.data.Location
import com.bikcode.rickandmortyapp.presentation.data.Origin
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.LocationEntity
import com.bikcode.rickandmortyapp.presentation.database.OriginEntity

val mockedOrigin = Origin(
    "",
    ""
)


val mockedLocation = Location(
    "",
    ""
)

val mockedCharacter = Character(
    0,
    "",
    "",
    "",
    "",
    "",
    mockedOrigin,
    mockedLocation,
    listOf(),
    false
)

val mockedOriginEntity = OriginEntity(
    "",
    ""
)


val mockedLocationEntity = LocationEntity(
    "",
    ""
)

val mockedCharacterEntity = CharacterEntity(
    0,
    "",
    "",
    "",
    "",
    "",
    mockedOriginEntity,
    mockedLocationEntity,
    listOf(),
    false
)

val mockedOriginServer = OriginServer(
    "",
    ""
)


val mockedLocationServer = LocationServer(
    "",
    ""
)

val mockedCharacterServer = CharacterServer(
    0,
    "",
    "",
    "",
    "",
    "",
    mockedOriginServer,
    mockedLocationServer,
    listOf(),
    false
)


