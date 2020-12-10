package com.bikcode.rickandmortyapp.presentation.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.databinding.ItemFavoriteCharacterBinding
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.util.bindImageUrl
import com.bikcode.rickandmortyapp.presentation.util.bindingInflate
import kotlinx.android.synthetic.main.item_favorite_character.view.*

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>() {

    private val characterList: MutableList<CharacterEntity> = mutableListOf()

    fun setData(data: List<CharacterEntity>) {
        characterList.clear()
        characterList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteListViewHolder(
        parent.bindingInflate(R.layout.item_favorite_character, false)
    )

    override fun getItemCount(): Int = characterList.count()

    fun getItem(position: Int): CharacterEntity = characterList[position]

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteListViewHolder(private val dataBinding: ItemFavoriteCharacterBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(item: CharacterEntity) {
            dataBinding.character = item
            itemView.item_favorite_character_tv_name.text = item.name
            itemView.item_favorite_character_iv_image.bindImageUrl(
                url = item.image,
                placeholder = R.drawable.ic_baseline_image_placeholder_24,
                errorPlaceholder = R.drawable.ic_baseline_broken_image_24
            )
        }
    }
}