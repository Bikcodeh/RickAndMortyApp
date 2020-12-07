package com.bikcode.rickandmortyapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.api.EpisodeServer

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private val episodeList: MutableList<EpisodeServer> = mutableListOf()

    fun setData(data: List<EpisodeServer>) {
        episodeList.clear()
        episodeList.addAll(data)
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

    fun getEpisode(position: Int) = episodeList[position]

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val item_episode_tv_episode =
            itemView.findViewById<TextView>(R.id.item_episode_tv_episode)
        private val item_episode_tv_episode_name =
            itemView.findViewById<TextView>(R.id.item_episode_tv_episode_name)
        private val item_episode_tv_episode_date =
            itemView.findViewById<TextView>(R.id.item_episode_tv_episode_date)

        fun bind(episode: EpisodeServer) {
            item_episode_tv_episode.text = episode.name
            item_episode_tv_episode_name.text = episode.episode
            item_episode_tv_episode_date.text = episode.date


        }
    }
}