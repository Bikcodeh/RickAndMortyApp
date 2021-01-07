package com.bikcode.rickandmortyapp

import android.util.Log
import io.reactivex.Maybe
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun normal() {
        Maybe.just(2)
            .isEmpty
            .flatMapMaybe {
                Log.d("value of ", it.toString())
                Maybe.just(it)
            }
            .test()
            .assertResult(false)
    }
}