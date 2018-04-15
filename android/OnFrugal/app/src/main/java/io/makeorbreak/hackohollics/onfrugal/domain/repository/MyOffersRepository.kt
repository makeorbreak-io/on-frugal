package io.makeorbreak.hackohollics.onfrugal.domain.repository

import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer

interface MyOffersRepository {
    fun getOffers(): List<Offer>
}