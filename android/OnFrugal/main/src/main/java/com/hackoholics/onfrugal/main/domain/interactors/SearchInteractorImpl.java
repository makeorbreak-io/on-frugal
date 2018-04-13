package com.hackoholics.onfrugal.main.domain.interactors;

import android.util.Log;

import com.hackoholics.onfrugal.main.data.OnlineSearchRepository;
import com.hackoholics.onfrugal.main.domain.executor.Executor;
import com.hackoholics.onfrugal.main.domain.executor.MainThread;
import com.hackoholics.onfrugal.main.domain.model.Search;
import com.hackoholics.onfrugal.main.domain.repository.SearchRepository;

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