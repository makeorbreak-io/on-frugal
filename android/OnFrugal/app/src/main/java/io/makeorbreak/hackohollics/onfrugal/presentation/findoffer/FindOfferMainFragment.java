package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.google.android.gms.maps.model.LatLng;

import io.makeorbreak.hackohollics.onfrugal.R;
import io.makeorbreak.hackohollics.onfrugal.data.remote.impl.SearchRepositoryImpl;
import io.makeorbreak.hackohollics.onfrugal.domain.executor.impl.ThreadExecutor;
import io.makeorbreak.hackohollics.onfrugal.domain.interactors.SearchInteractorImpl;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Search;
import io.makeorbreak.hackohollics.onfrugal.domain.model.User;
import io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.searchList.ChildRow;
import io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.searchList.ParentRow;
import io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.searchList.SearchExpandableListAdapter;
import io.makeorbreak.hackohollics.onfrugal.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.Objects;

public class FindOfferMainFragment extends Fragment implements OnSearchViewListener {

    private static final String TAG = FindOfferMainFragment.class.getSimpleName();

    private BaseMaterialSearchView mSearchView;
    private SearchExpandableListAdapter mSearchListAdapter;
    private ExpandableListView mSearchList;
    private ArrayList<ParentRow> mCategoriesList = new ArrayList<>();
    private View mRootView;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_find_offers, container, false);

        Toolbar myToolbar = mRootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(myToolbar);

        TabLayout tabLayout = mRootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = mRootView.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mSearchView = mRootView.findViewById(R.id.sv);
        mSearchView.setOnSearchViewListener(this);

        setHasOptionsMenu(true);
        getActivity().setTitle(null);
        mCategoriesList = new ArrayList<>();
        displaySearchResults(mRootView);
        expandCategories();

        return mRootView;
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private int mNumOfTabs;

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        double lat =41.1483846;
        double lng = -8.6129637;


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FindOfferListTabFragment fragment = new FindOfferListTabFragment();
                    fragment.setLat(lat); fragment.setLng(lng);

                    return fragment;
                case 1:
                    return new FindOfferMapTabFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

    }

    private void searchForResults(String query) {

        SearchInteractorImpl.Callback callBack = new SearchInteractorImpl.Callback() {

            @Override
            public void onSuccess(Search search) {
                onSearchPoiFetch(search);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(),getString(R.string.error_connection),Toast.LENGTH_SHORT).show();
            }
        };

        SearchInteractorImpl searchInteractor = new SearchInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                callBack,
                new SearchRepositoryImpl(Objects.requireNonNull(getContext())),
                query
        );
        searchInteractor.execute();

        Log.d(TAG, "Searched Query: " + query);
    }

    private void onSearchPoiFetch(Search searchModel) {

        mCategoriesList = new ArrayList<>();

        if (!searchModel.getOffers().isEmpty()) {
            ArrayList<ChildRow> childRowsOffers = new ArrayList<>();
            Offer offer;
            ParentRow parentRow;
            for (int i = 0; i < searchModel.getOffers().size(); i++) {
                offer = searchModel.getOffers().get(i);
                Log.d(TAG, "POI MODEL SEARCH: " + offer.getName());
                childRowsOffers.add(new ChildRow(R.drawable.ic_local_offer_black_24dp, offer));
            }
            parentRow = new ParentRow("Offers", childRowsOffers);
            mCategoriesList.add(parentRow);
        }

        if (!searchModel.getUsers().isEmpty()) {
            ArrayList<ChildRow> childRowsUsers = new ArrayList<>();
            User user;
            ParentRow parentRow;
            for (int i = 0; i < searchModel.getUsers().size(); i++) {
                user = searchModel.getUsers().get(i);
                Log.d(TAG, "ROUTE MODEL SEARCH: " + user.getName());
                childRowsUsers.add(new ChildRow(R.drawable.ic_person_black_24dp, user));
            }
            parentRow = new ParentRow("Users", childRowsUsers);
            mCategoriesList.add(parentRow);
        }

        displaySearchResults(mRootView);
        expandCategories();
    }

    private void expandCategories() {
        int count = mSearchListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            mSearchList.expandGroup(i);
        }
    }

    private void displaySearchResults(View rooView) {
        mSearchList = rooView.findViewById(R.id.expandableListView_search);
        mSearchListAdapter = new SearchExpandableListAdapter(getActivity(), mCategoriesList);
        mSearchList.setAdapter(mSearchListAdapter);
    }

    @Override
    public void onSearchViewShown() {
        Log.d(TAG, "onSearchViewShown: clicked");
    }

    @Override
    public void onSearchViewClosed() {
        Log.d(TAG, "onSearchViewClosed: clicked");
        mCategoriesList = new ArrayList<>();
        displaySearchResults(mRootView);
        expandCategories();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: " + query);
        if (query.length() != 0) {
            searchForResults(query);
        } else {
            mCategoriesList = new ArrayList<>();
            displaySearchResults(mRootView);
        }
        expandCategories();
        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: " + newText);
        if (newText.length() != 0) {
            searchForResults(newText);
        } else {
            mCategoriesList = new ArrayList<>();
            displaySearchResults(mRootView);
        }
        expandCategories();
    }
}
