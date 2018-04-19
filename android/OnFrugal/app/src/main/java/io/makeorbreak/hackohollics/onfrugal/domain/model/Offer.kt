package io.makeorbreak.hackohollics.onfrugal.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class Offer(id: String,name: String,
            var host: User,
            var description: String,
            var endDate: Date,
            var location: String,
            var spots: Int,
            var price: Float): Serializable, BasicModel(id,name) {

    var candidates= ArrayList<User>()
    var accepted= ArrayList<User>()

    fun getNumberCandidates(): Int {
        return candidates.size
    }

    fun getNumberAccepted(): Int{
        return accepted.size
    }

    override fun toString(): String {
        return "Offer(uid=$uid, name=$name, host=$host, description='$description', endDate=$endDate, location='$location', spots=$spots, price=$price, candidates=$candidates, accepted=$accepted)"
    }


}