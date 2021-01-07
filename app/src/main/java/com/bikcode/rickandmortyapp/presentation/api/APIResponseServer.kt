package com.bikcode.rickandmortyapp.presentation.api

import android.os.Parcelable
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_DATE
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_EPISODE
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_GENDER
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_ID
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_IMAGE
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_LOCATION
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_NAME
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_ORIGIN
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_SPECIES
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_STATUS
import com.bikcode.rickandmortyapp.presentation.api.APIConstants.KEY_URL
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CharacterResponseServer(
    @SerializedName(APIConstants.KEY_RESULTS) val results: List<CharacterServer>
)

@Parcelize
data class CharacterServer(
    @SerializedName(KEY_ID) val id: Int,
    @SerializedName(KEY_NAME) val name: String,
    @SerializedName(KEY_IMAGE) val image: String?,
    @SerializedName(KEY_GENDER) val gender: String,
    @SerializedName(KEY_SPECIES) val species: String,
    @SerializedName(KEY_STATUS) val status: String,
    @SerializedName(KEY_ORIGIN) val origin: OriginServer,
    @SerializedName(KEY_LOCATION) val location: LocationServer,
    @SerializedName(KEY_EPISODE) val episodeList: List<String>,
    val isFavorite: Boolean = false
): Parcelable

@Parcelize
data class LocationServer(
    @SerializedName(KEY_NAME) val name: String,
    @SerializedName(KEY_URL) val url: String
): Parcelable

@Parcelize
data class OriginServer(
    @SerializedName(KEY_NAME) val name: String,
    @SerializedName(KEY_URL) val url: String
): Parcelable

data class EpisodeServer(
    @SerializedName(KEY_ID) val id: Int,
    @SerializedName(KEY_NAME) val name: String,
    @SerializedName(KEY_DATE) val date: String,
    @SerializedName(KEY_EPISODE) val episode: String
)
