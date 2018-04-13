package com.hackoholics.onfrugal.main.data

import android.content.Context
import com.hackoholics.onfrugal.main.domain.model.Search
import com.hackoholics.onfrugal.main.domain.repository.SearchRepository

class OnlineSearchRepository(var context: Context) : SearchRepository {
    override fun search(query: String, latitude: Double, longitude: Double): Search {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}