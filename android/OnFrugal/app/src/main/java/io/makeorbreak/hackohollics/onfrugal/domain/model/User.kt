package io.makeorbreak.hackohollics.onfrugal.domain.model

import java.io.Serializable

class User(uid: String,
           name: String,
           var email: String,
           var phoneNumber: String): Serializable, BasicModel(uid,name) {

}