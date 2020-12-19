package com.bikcode.rickandmortyapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bikcode.rickandmortyapp.util.RxSchedulerRule
import com.bikcode.rickandmortyapp.presentation.api.*
import com.bikcode.rickandmortyapp.presentation.data.character.CharacterRepositoryImpl
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterLocalRepository
import com.bikcode.rickandmortyapp.presentation.database.toCharacterEntity
import com.bikcode.rickandmortyapp.viewmodel.mockedCharacter
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: APIService

    @Mock
    private lateinit var characterLocalRepository: CharacterLocalRepository

    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun setUp() {
        characterRepositoryImpl = CharacterRepositoryImpl(apiService, characterLocalRepository)
    }

    @Test
    fun `getCharacters should return a character list observable`() {

        val response = CharacterResponseServer(
            listOf(
                mockedCharacterServer
            )
        )
        val expectedResult = Single.just(response)
        val expectedList = listOf(
            mockedCharacter
        )

        given(apiService.getCharacters()).willReturn(expectedResult)

        val testObserver = characterRepositoryImpl.getCharacters().test()

        testObserver.awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertValueCount(1)
            .assertComplete()
            .assertResult(expectedList)

        Mockito.verify(apiService).getCharacters()

        Mockito.verifyNoMoreInteractions(apiService)
        Mockito.verifyZeroInteractions(apiService)
    }

    @Test
    fun `getFavoriteCharacterStatus should return a boolean observable`() {
        val expectedEntity = Maybe.just(mockedCharacterServer.toCharacterEntity())

        Mockito.`when`(characterLocalRepository.getCharacterById(Mockito.anyInt()))
            .thenReturn(expectedEntity)

        val testObserver =
            characterRepositoryImpl.getFavoriteCharacterStatus(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(true)
            .assertResult(true)

        Mockito.verify(characterLocalRepository).getCharacterById(ArgumentMatchers.anyInt())

        Mockito.verifyZeroInteractions(characterLocalRepository)
        Mockito.verifyNoMoreInteractions(characterLocalRepository)
    }

    @Test
    fun `updateFavoriteCharacterStatus should return false `() {
        val expectedEntity = Maybe.just(mockedCharacterServer.toCharacterEntity())

        Mockito.`when`(characterLocalRepository.getCharacterById(Mockito.anyInt()))
            .thenReturn(expectedEntity)

        val mockedEntity = mockedCharacterServer.toCharacterEntity()

        val testObserver =
            characterRepositoryImpl.updateFavoriteCharacterStatus(mockedEntity).test()

        testObserver.awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(false)
            .assertResult(false)
    }
}

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
    listOf()
)
