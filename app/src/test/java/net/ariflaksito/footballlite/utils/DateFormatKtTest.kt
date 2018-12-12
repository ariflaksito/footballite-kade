package net.ariflaksito.footballlite.utils

import org.junit.Test
import org.junit.Assert.*

class DateFormatKtTest {

    @Test
    fun formatDate() {
        val d = "2018-12-02"
        assertEquals("Sun, 02 Dec 2018", formatDate(d))
    }
}