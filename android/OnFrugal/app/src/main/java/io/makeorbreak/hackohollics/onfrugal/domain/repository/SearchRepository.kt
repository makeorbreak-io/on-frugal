package io.makeorbreak.hackohollics.onfrugal.domain.repository

import io.makeorbreak.hackohollics.onfrugal.domain.model.Search

interface SearchRepository {

    fun search(query: String, latitude: Double, longitude: Double ): Search

    fun search(query: String): Search
}