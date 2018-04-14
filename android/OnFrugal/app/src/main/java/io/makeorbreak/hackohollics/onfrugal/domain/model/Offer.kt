package io.makeorbreak.hackohollics.onfrugal.domain.model

import android.location.Location
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class Offer(var name: String,
            var host: User,
            var description: String,
            var endDate: Date,
            var location: Location,
            var spots: Int,
            var price: Float): Serializable {

    var candidates= ArrayList<User>()
    var accepted= ArrayList<User>()

}