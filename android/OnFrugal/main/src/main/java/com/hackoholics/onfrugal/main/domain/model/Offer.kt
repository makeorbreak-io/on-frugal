package com.hackoholics.onfrugal.main.domain.model

import android.location.Location
import java.util.*
import kotlin.collections.ArrayList


class Offer(var name: String,
            var host: User,
            var description: String,
            var endDate: Date,
            var location: Location,
            var spots: Int,
            var price: Float) {

    var candidates= ArrayList<User>()
    var accepted= ArrayList<User>()

}