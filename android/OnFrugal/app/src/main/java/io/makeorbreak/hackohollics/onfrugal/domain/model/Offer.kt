package io.makeorbreak.hackohollics.onfrugal.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class Offer(val id: String,
            var name: String,
            var host: User,
            var description: String,
            var endDate: Date,
            var location: String,
            var spots: Int,
            var price: Float): Serializable{

    var candidates= ArrayList<User>()
    var accepted= ArrayList<User>()

    fun getNumberCandidates(): Int {
        return candidates.size
    }

    fun getNumberAccepted(): Int{
        return accepted.size
    }
}