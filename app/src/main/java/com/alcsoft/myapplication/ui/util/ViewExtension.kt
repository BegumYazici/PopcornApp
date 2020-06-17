package com.alcsoft.myapplication.ui.util

import android.view.View

fun View.toVisible(){
    visibility = View.VISIBLE
}

fun View.toInvisible(){
    visibility = View.GONE
}