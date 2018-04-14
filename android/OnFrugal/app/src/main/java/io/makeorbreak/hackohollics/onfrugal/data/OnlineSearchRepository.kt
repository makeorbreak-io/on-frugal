package io.makeorbreak.hackohollics.onfrugal.data

import android.content.Context
import io.makeorbreak.hackohollics.onfrugal.domain.model.Search
import io.makeorbreak.hackohollics.onfrugal.domain.repository.SearchRepository

class OnlineSearchRepository(var context: Context) : SearchRepository {
    override fun search(query: String, latitude: Double, longitude: Double): Search {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}