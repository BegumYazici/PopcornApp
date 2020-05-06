package com.alcsoft.myapplication.ui.movies.adapter

import com.alcsoft.myapplication.ui.movies.model.PeopleModel

interface PeopleListener {

    fun onPeopleItemClicked(peopleModel: PeopleModel)
}