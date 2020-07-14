package com.begicim.popcornapp

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {

        val stringDate = "05-05-2020"

        val date = SimpleDateFormat("dd-MM-yyyy").parse(stringDate)

        val formattedDate = SimpleDateFormat("dd.MM.yy").format(date)

        assertEquals("05.05.2020", formattedDate)
    }
}
