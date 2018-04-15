package io.makeorbreak.hackohollics.onfrugal.domain.interactors;

import android.util.Log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.makeorbreak.hackohollics.onfrugal.domain.executor.Executor;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.MainThread;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.MyOffersRepository;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.OfferRepository;

public class MyOffersInteractorImpl extends AbstractInteractor{
    private final static String TAG = MyOffersInteractorImpl.class.getSimpleName();
    private final Callback callback;

    private MyOffersRepository mRepository;
    private double mLatitude;
    private double mLongitude;

    public interface Callback{
        void onSuccess(List<Offer> pastOffers, List<Offer> ongoingOffers);

        void onError(String error);

    }

    public MyOffersInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  Callback callback,
                                  MyOffersRepository mRepository) {
        super(threadExecutor, mainThread);
        this.mRepository = mRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            List<Offer> offers;
            Log.d(TAG, "Start search");
            offers = mRepository.getOffers();
            Log.d(TAG, "Finished search");
            if(offers == null)
                throw new Exception("Search cameback null");
            notifySuccess(offers);
        } catch (Exception e) {
            notifyError(e.getClass().toString(), e.getMessage());
        }
    }

    void notifySuccess(final List<Offer> offers){
        final List<Offer> pastOffers = new ArrayList<>();
        final List<Offer> ongoingOffers = new ArrayList<>();

        for(Offer offer: offers){
            if(offer.getEndDate().compareTo(new Date()) < 0)
                pastOffers.add(offer);
            else
                ongoingOffers.add(offer);
        }


        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(pastOffers,ongoingOffers);
            }
        });

    }

    void notifyError(final String code, final String errorMessage){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code + " - " +errorMessage);
            }
        });

    }
}