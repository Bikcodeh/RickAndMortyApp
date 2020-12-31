package com.bikcode.rickandmortyapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.api.CharacterServer
import com.bikcode.rickandmortyapp.presentation.data.Character
import com.bikcode.rickandmortyapp.presentation.parcelables.CharacterParcelable
import com.bikcode.rickandmortyapp.presentation.parcelables.toCharacterServer
import com.bikcode.rickandmortyapp.presentation.ui.adapter.EpisodeAdapter
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import com.bikcode.rickandmortyapp.presentation.util.showLongToast
import com.bikcode.rickandmortyapp.presentation.viewmodel.CharacterDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailActivity : AppCompatActivity() {

    private val characterDetailViewModel: CharacterDetailViewModel by viewModel()
    private var characterParcelable: CharacterParcelable? = null
    private val episodeAdapter: EpisodeAdapter by lazy {
        EpisodeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        initComponents()

        characterDetailViewModel.events.observe(this, Observer(this::validateEvents))
        characterDetailViewModel.isFavorite.observe(this, Observer(this::validateFavoriteStatus))

        characterParcelable?.let {
            characterDetailViewModel.getEpisodes(it.episodeList)
            characterDetailViewModel.validateCharacter(it.toCharacterServer())
            detail_iv_favorite.setOnClickListener { _ ->
                characterDetailViewModel.updateFavoriteCharacterStatus(it.toCharacterServer()) { favorite ->
                    validateFavoriteStatus(favorite)
                }
            }
        }
    }


    private fun validateEvents(event: Event<CharacterDetailViewModel.CharacterDetailEvent>?) {
        event?.getContentIfNotHandled()?.let { state ->
            when (state) {
                is CharacterDetailViewModel.CharacterDetailEvent.ShowListEpisodes -> {
                    episodeAdapter.setData(state.list)
                    detail_rv_episodes.apply {
                        adapter = episodeAdapter
                    }
                }
                is CharacterDetailViewModel.CharacterDetailEvent.ShowErrorList -> {
                    baseContext.showLongToast(state.error)
                }
                CharacterDetailViewModel.CharacterDetailEvent.ShowLoadingListEpisodes -> detail_pb_progress.visibility =
                    View.VISIBLE
                CharacterDetailViewModel.CharacterDetailEvent.HideLoadingListEpisodes -> detail_pb_progress.visibility =
                    View.GONE
                CharacterDetailViewModel.CharacterDetailEvent.CloseActivity -> {
                    baseContext.showLongToast(getString(R.string.no_data_character))
                    finish()
                }
            }
        }
    }

    private fun validateFavoriteStatus(isFavorite: Int) {
        detail_iv_favorite.setImageResource(
            if (isFavorite == 1) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
        )
    }

    private fun initComponents() {
        intent?.let {
            characterParcelable = it.getParcelableExtra<CharacterParcelable>("user")
            bind(characterParcelable)
        }
    }

    private fun bind(characterParcelable: CharacterParcelable?) {
        characterParcelable?.let { data ->
            Picasso.get().load(data.image)
                .placeholder(R.drawable.ic_baseline_image_placeholder_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(detail_civ_image_character)
            detail_tv_name.text = data.name
            detail_tv_planet.text = data.location.name
            detail_tv_specie.text = data.species
            detail_tv_gender.text = data.gender
            detail_tv_status.text = data.status
            detail_tv_origin.text = data.origin.name
            /*if(data.isFavorite) {
                detail_iv_favorite.setImageResource(R.drawable.ic_favorite)
            } else {
                detail_iv_favorite.setImageResource(R.drawable.ic_favorite_border)
            }*/
        }
    }
}