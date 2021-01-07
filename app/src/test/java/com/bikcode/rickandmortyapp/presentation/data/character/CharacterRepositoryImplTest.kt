package com.bikcode.rickandmortyapp.presentation.data.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bikcode.rickandmortyapp.presentation.api.APIService
import com.bikcode.rickandmortyapp.presentation.api.CharacterResponseServer
import com.bikcode.rickandmortyapp.presentation.database.CharacterEntity
import com.bikcode.rickandmortyapp.presentation.database.character.CharacterDao
import com.bikcode.rickandmortyapp.util.*
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryImplTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: APIService

    @Mock
    private lateinit var characterDao: CharacterDao

    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun setUp() {
        characterRepositoryImpl = CharacterRepositoryImpl(apiService, characterDao)
    }

    @Test
    fun `validate if there are characters saved in db`() = runBlocking {

        //Given
        val expectedResult: Int = 2

        //When
        Mockito.`when`(characterDao.countCharacters()).thenReturn(2)
        val result = characterRepositoryImpl.isEmptyCharacters()

        //Then
        assertNotNull(result)
        assertEquals(expectedResult, result)

        Mockito.verify(characterDao, Mockito.times(1)).countCharacters()
        Mockito.verifyZeroInteractions(characterDao)
    }

    @Test
    fun `get all character from remote service`() {
        //Given
        val response = CharacterResponseServer(
            listOf(
                mockedCharacterServer
            )
        )
        val expectedResult = Single.just(response)
        val expectedList = listOf(
            mockedCharacter
        )

        BDDMockito.given(apiService.getCharacters()).willReturn(expectedResult)

        //When
        val testObserver = characterRepositoryImpl.getCharacters().test()

        //Then
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
    fun `get all characters from db`() = runBlocking {

        //Given
        val expectedResult = MutableLiveData<List<CharacterEntity>>()
        val expectedList = listOf(mockedCharacterEntity)
        expectedResult.value = expectedList

        //When
        Mockito.`when`(characterDao.getAllCharacters()).thenReturn(expectedResult)
        val data = characterRepositoryImpl.getAllCharactersDB().getValueBlocking()
        //Then
        assertEquals(expectedList, data)

        Mockito.verify(characterDao, Mockito.times(1)).getAllCharacters()
        Mockito.verifyZeroInteractions(characterDao)
    }

    @Test
    fun `get all favorite characters from db`() {
        //Given
        mockedCharacterEntity.statusFavorite = true
        val expectedResult = MutableLiveData<List<CharacterEntity>>()
        val expectedList = listOf(mockedCharacterEntity)
        expectedResult.value = expectedList
        //When
        Mockito.`when`(characterDao.getAllFavoriteCharacters()).thenReturn(expectedResult)
        val data = characterRepositoryImpl.getCharactersDB().getValueBlocking()
        //Then
        assertEquals(expectedList, data)
        assertEquals(true, data?.get(0)?.statusFavorite)

        Mockito.verify(characterDao, Mockito.times(1)).getAllFavoriteCharacters()
        Mockito.verifyZeroInteractions(characterDao)
    }
}