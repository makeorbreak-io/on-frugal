package io.makeorbreak.hackohollics.onfrugal.domain.repository

import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer

interface OfferRepository {

    fun getOffer(uid: String): Offer

    fun getOffers(lat: Double, lng: Double): List<Offer>
}