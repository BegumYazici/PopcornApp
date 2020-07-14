package com.begicim.popcornapp.ui.util

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextDispatcher {

    fun io() : CoroutineDispatcher

    fun main() : CoroutineDispatcher
}