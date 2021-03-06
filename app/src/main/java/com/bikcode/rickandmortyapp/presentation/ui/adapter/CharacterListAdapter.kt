package com.bikcode.rickandmortyapp.presentation.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.databinding.ItemCharacterBinding
import com.bikcode.rickandmortyapp.interfaces.CharacterCallback
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.util.bindImageUrl
import com.bikcode.rickandmortyapp.presentation.util.bindingInflate

class CharacterListAdapter(private val characterCallback: CharacterCallback) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    private val characterList: MutableList<Character> = mutableListOf()

    fun setData(newCharacterList: List<Character>) {
        val diffCallback = CharacterDiffCallback(characterList, newCharacterList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.characterList.clear()
        this.characterList.addAll(newCharacterList)
        diffResult.dispatchUpdatesTo(this)
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

        fun bind(character: Character) {
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