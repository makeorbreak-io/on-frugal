package io.makeorbreak.hackohollics.onfrugal.domain.interactors;

import android.util.Log;

import io.makeorbreak.hackohollics.onfrugal.data.OnlineSearchRepository;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.Executor;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.MainThread;

import io.makeorbreak.hackohollics.onfrugal.domain.model.Search;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.SearchRepository;

public class SearchInteractorImpl extends AbstractInteractor{
    private final static String TAG = SearchInteractorImpl.class.getSimpleName();
    private final Callback callback;

    private SearchRepository mRepository;
    private String mSearchedQuery;
    private double mLatitude;
    private double mLongitude;

    // TODO: 13-04-2018 implement callback
    public interface Callback{

    }

    public SearchInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                OnlineSearchRepository mRepository,
                                String mSearchedQuery,
                                double mLatitude, double mLongitude) {
        super(threadExecutor, mainThread);
        this.mRepository = mRepository;
        this.mSearchedQuery = mSearchedQuery;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            Search searchModel;
            Log.d(TAG, "Start search");
            searchModel = mRepository.search(mSearchedQuery, mLatitude, mLongitude);
            Log.d(TAG, "Finished search");
//            notifySuccess(searchModel);
        } catch (Exception e) {
//            notifyError(e.getCode(), e.getErrorMessage());
        }
    }
}