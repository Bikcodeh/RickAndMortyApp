package com.bikcode.rickandmortyapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.databinding.ItemCharacterBinding
import com.bikcode.rickandmortyapp.interfaces.CharacterCallback
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.util.bindImageUrl
import com.bikcode.rickandmortyapp.presentation.util.bindingInflate

class CharacterListAdapter(private val characterCallback: CharacterCallback) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    private val characterList: MutableList<CharacterServer> = mutableListOf()

    fun setData(characterList: List<CharacterServer>) {
        this.characterList.clear()
        this.characterList.addAll(characterList)
        notifyDataSetChanged()
    }

    fun getCharacterItem(position: Int) = characterList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterListViewHolder(
            parent.bindingInflate(R.layout.item_character, false)
        )

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(getCharacterItem(position))
        holder.bindClick()
    }

    inner class CharacterListViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imageCharacter: ImageView = itemView.findViewById(R.id.item_character_iv_image)

        fun bind(character: CharacterServer) {
            with(binding) {
                character.run {
                    itemCharacterTvName.text = name
                    itemCharacterIvImage.bindImageUrl(
                        image,
                        R.drawable.ic_baseline_image_placeholder_24,
                        R.drawable.ic_baseline_broken_image_24
                    )
                }
            }
        }
        fun bindClick() {
            itemView.setOnClickListener { characterCallback.onCharacterClick(adapterPosition, imageCharacter) }
        }
    }
}