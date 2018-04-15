package io.makeorbreak.hackohollics.onfrugal.data.remote.impl;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.MyOffersRepository;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.OfferRepository;

public class MyAttendingOffersRepository extends OfferRepositoryImpl implements MyOffersRepository {
    public MyAttendingOffersRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public MyAttendingOffersRepository(Context context) {
        super(context);
    }

    @NotNull
    @Override
    public List<Offer> getOffers() {
        return this.getOffersAttending();
    }
}
