package com.bikcode.rickandmortyapp.interfaces

import android.widget.ImageView

interface CharacterCallback {

    fun onCharacterClick(
        pos: Int,
        characterImage: ImageView
    )
}