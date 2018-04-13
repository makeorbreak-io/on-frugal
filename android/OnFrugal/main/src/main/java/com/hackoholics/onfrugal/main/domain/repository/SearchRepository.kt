package com.hackoholics.onfrugal.main.domain.repository

import com.hackoholics.onfrugal.main.domain.model.Search

interface SearchRepository {

    fun search(query: String, latitude: Double, longitude: Double ): Search
}