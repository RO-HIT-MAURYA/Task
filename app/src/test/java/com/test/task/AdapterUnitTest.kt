package com.test.task

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AdapterUnitTest {

    @Test
    fun testGetPercent() {
        val input = 3.1f
        val expected = 31

        val output = RecyclerViewAdapter.getPercent(input)

        assertEquals(expected, output)
    }

    @Test
    fun testChangeDateFormat() {
        val input = "1995-01-05"
        val expected = "Jan 05, 1995"

        val output = RecyclerViewAdapter.changeDateFormat(input)

        assertEquals(expected, output)
    }
}