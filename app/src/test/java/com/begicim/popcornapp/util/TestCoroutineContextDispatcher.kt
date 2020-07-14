package com.begicim.popcornapp.util

import com.begicim.popcornapp.ui.util.CoroutineContextDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestCoroutineContextDispatcher : CoroutineContextDispatcher {

    val testCoroutineDispatcher = TestCoroutineDispatcher()
        .apply {
           
        }

    override fun io(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    override fun main(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }
}