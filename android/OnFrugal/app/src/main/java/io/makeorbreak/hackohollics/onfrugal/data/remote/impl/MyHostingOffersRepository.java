package io.makeorbreak.hackohollics.onfrugal.data.remote.impl;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.MyOffersRepository;

public class MyHostingOffersRepository extends OfferRepositoryImpl implements MyOffersRepository {
    public MyHostingOffersRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public MyHostingOffersRepository(Context context) {
        super(context);
    }

    @NotNull
    @Override
    public List<Offer> getOffers() {
        return this.getOffersHosting();
    }
}
