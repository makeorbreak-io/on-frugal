package io.makeorbreak.hackohollics.onfrugal.domain.interactors;

import android.util.Log;

import java.util.List;

import io.makeorbreak.hackohollics.onfrugal.domain.executor.Executor;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.MainThread;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Search;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.OfferRepository;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.SearchRepository;

public class OfferInteractorLocationImpl extends AbstractInteractor{
    private final static String TAG = OfferInteractorLocationImpl.class.getSimpleName();
    private final Callback callback;

    private OfferRepository mRepository;
    private double mLatitude;
    private double mLongitude;

    public interface Callback{
        void onSuccess(List<Offer> offers);

        void onError(String error);

    }

    public OfferInteractorLocationImpl(Executor threadExecutor,
                                       MainThread mainThread,
                                       Callback callback,
                                       OfferRepository mRepository,
                                       double lat, double lng) {
        super(threadExecutor, mainThread);
        this.mRepository = mRepository;
        this.callback = callback;
        this.mLatitude = lat;
        this.mLongitude = lng;
    }

    @Override
    public void run() {
        try {
            List<Offer> offers;
            Log.d(TAG, "Start search");
            offers = mRepository.getOffers(mLatitude,mLongitude);
            Log.d(TAG, "Finished Interactor by Location");
            Log.d(TAG, offers.toString());
            if(offers == null)
                throw new Exception("Search cameback null");
            notifySuccess(offers);
        } catch (Exception e) {
            notifyError(e.getClass().toString(), e.getMessage());
        }
    }

    void notifySuccess(final List<Offer> offers){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(offers);
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