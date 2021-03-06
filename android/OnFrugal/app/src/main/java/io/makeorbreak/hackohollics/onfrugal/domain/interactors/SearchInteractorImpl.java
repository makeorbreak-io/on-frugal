package io.makeorbreak.hackohollics.onfrugal.domain.interactors;

import android.util.Log;

import io.makeorbreak.hackohollics.onfrugal.domain.executor.Executor;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.MainThread;

import io.makeorbreak.hackohollics.onfrugal.domain.model.Search;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.SearchRepository;

public class SearchInteractorImpl extends AbstractInteractor{
    private final static String TAG = SearchInteractorImpl.class.getSimpleName();
    private final Callback callback;

    private SearchRepository mRepository;
    private String mSearchedQuery;

    public interface Callback{
        void onSuccess(Search search);

        void onError(String error);

    }

    public SearchInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                SearchRepository mRepository,
                                String mSearchedQuery) {
        super(threadExecutor, mainThread);
        this.mRepository = mRepository;
        this.mSearchedQuery = mSearchedQuery;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            Search searchModel;
            Log.d(TAG, "Start search");
            searchModel = mRepository.search(mSearchedQuery);
            Log.d(TAG, "Finished search");
            if(searchModel == null)
                throw new Exception("Search cameback null");
            notifySuccess(searchModel);
        } catch (Exception e) {
            notifyError(e.getClass().toString(), e.getMessage());
        }
    }

    void notifySuccess(final Search search){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(search);
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