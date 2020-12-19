package com.bikcode.rickandmortyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bikcode.rickandmortyapp.util.RxSchedulerRule
import com.bikcode.rickandmortyapp.presentation.data.*
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.ui.utils.Event
import com.bikcode.rickandmortyapp.presentation.viewmodel.HomeViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.BDDMockito.*
import org.mockito.Mockito


@RunWith(MockitoJUnitRunner::class)
class TestHomeViewModel {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Mock
    lateinit var eventObserver: Observer<Event<HomeViewModel.CharacterState>>

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(characterRepositoryImpl)
    }

    @Test
    fun `onGetAllCharacters should an expected success list of characters`() {
        val expectedResult = listOf(
            mockedCharacter.copy(id = 1)
        )

        given(characterRepositoryImpl.getCharacters()).willReturn(Single.just(expectedResult))

        homeViewModel.events.observeForever(eventObserver)

        homeViewModel.getCharacters()

        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.ShowLoading))
        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.HideLoading))
        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.ShowCharacterList(expectedResult)))

        Mockito.verify(characterRepositoryImpl, Mockito.times(1)).getCharacters()

        Mockito.verifyNoMoreInteractions(characterRepositoryImpl)
        Mockito.verifyNoMoreInteractions(eventObserver)
    }

    @Test
    fun `onGetAllCharacters should return a error`() {
        val expectedResult = Throwable("Error")
        Mockito.`when`(characterRepositoryImpl.getCharacters()).thenReturn(Single.error(expectedResult))

        homeViewModel.events.observeForever(eventObserver)

        homeViewModel.getCharacters()

        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.ShowLoading))
        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.HideLoading))
        Mockito.verify(eventObserver).onChanged(Event(HomeViewModel.CharacterState.ShowCharacterError(expectedResult.message!!)))

        Mockito.verify(characterRepositoryImpl, Mockito.times(1)).getCharacters()

        Mockito.verifyNoMoreInteractions(characterRepositoryImpl)
        Mockito.verifyNoMoreInteractions(eventObserver)
    }
}

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
    listOf()
)

