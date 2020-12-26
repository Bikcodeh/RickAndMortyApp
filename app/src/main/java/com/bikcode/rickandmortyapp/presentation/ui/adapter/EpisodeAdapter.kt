package com.bikcode.rickandmortyapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.api.EpisodeServer
import kotlinx.android.synthetic.main.item_episode.view.*
import kotlin.properties.Delegates

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    //private val episodeList: MutableList<EpisodeServer> = mutableListOf()

     var episodeList: MutableList<EpisodeServer> by Delegates.observable(mutableListOf()) { _, _, _  ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        )

    override fun getItemCount(): Int = episodeList.count()

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getEpisode(position))
    }

    private fun getEpisode(position: Int) = episodeList[position]

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(episode: EpisodeServer) {
            itemView.item_episode_tv_episode.text = episode.name
            itemView.item_episode_tv_episode_name.text = episode.episode
            itemView.item_episode_tv_episode_date.text = episode.date
        }
    }
}