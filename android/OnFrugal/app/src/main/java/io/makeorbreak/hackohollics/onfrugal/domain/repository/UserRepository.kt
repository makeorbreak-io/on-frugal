package io.makeorbreak.hackohollics.onfrugal.domain.repository

import android.net.Uri

interface UserRepository {

    fun getDisplayName(): String

    fun getToken(): String

    fun getUID(): String

    fun getPhotoUrl(): Uri
    fun getEmail(): String
}