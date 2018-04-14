package com.hackoholics.onfrugal.main.presentation.findoffer;

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

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.google.android.gms.maps.model.LatLng;
import com.hackoholics.onfrugal.main.R;
import com.hackoholics.onfrugal.main.data.OnlineSearchRepository;
import com.hackoholics.onfrugal.main.domain.executor.impl.ThreadExecutor;
import com.hackoholics.onfrugal.main.domain.interactors.SearchInteractorImpl;
import com.hackoholics.onfrugal.main.domain.model.Search;
import com.hackoholics.onfrugal.main.presentation.findoffer.map.FindOfferMapFragment;
import com.hackoholics.onfrugal.main.presentation.findoffer.FindOfferMapTabFragment;
import com.hackoholics.onfrugal.main.presentation.findoffer.searchList.ParentRow;
import com.hackoholics.onfrugal.main.presentation.findoffer.searchList.SearchExpandableListAdapter;
import com.hackoholics.onfrugal.main.threading.MainThreadImpl;

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

        mSearchView = mRootView.findViewById(R.id.sv);
        mSearchView.setOnSearchViewListener(this);


        setHasOptionsMenu(true);
        getActivity().setTitle(null);
        mCategoriesList = new ArrayList<>();
        displaySearchResults(mRootView);
        expandCategories();

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

        return mRootView;
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private int mNumOfTabs;

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FindOfferListTabFragment();
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

        };

        LatLng latLng = new LatLng(0,0);

        SearchInteractorImpl searchInteractor = new SearchInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                callBack,
                new OnlineSearchRepository(Objects.requireNonNull(getContext())),
                query, latLng.latitude, latLng.longitude
        );
        searchInteractor.execute();

        Log.d(TAG, "Searched Query: " + query + " Lat: " + latLng.latitude + " Lng: " + latLng.longitude);
    }

    private void onSearchPoiFetch(Search searchModel) {

        mCategoriesList = new ArrayList<>();

        // TODO: 13-04-2018 Adapt for search with users, offers, etc...
        /*if (!searchModel.getPois().isEmpty()) {
            ArrayList<ChildRow> childRowsPois = new ArrayList<>();
            PoiModel model;
            ParentRow parentRow;
            for (int i = 0; i < searchModel.getPois().size(); i++) {
                model = searchModel.getPois().get(i);
                Log.d(TAG, "POI MODEL SEARCH: " + model.getName());
                childRowsPois.add(new ChildRow(R.drawable.map_marker, model));
            }
            parentRow = new ParentRow("Points of Interest", childRowsPois);
            mCategoriesList.add(parentRow);
        }

        if (!searchModel.getRoutes().isEmpty()) {
            ArrayList<ChildRow> childRowsRoutes = new ArrayList<>();
            RouteModel model;
            ParentRow parentRow;
            for (int i = 0; i < searchModel.getRoutes().size(); i++) {
                model = searchModel.getRoutes().get(i);
                Log.d(TAG, "ROUTE MODEL SEARCH: " + model.getName());
                childRowsRoutes.add(new ChildRow(R.drawable.walk, model));
            }
            parentRow = new ParentRow("Routes", childRowsRoutes);
            mCategoriesList.add(parentRow);
        }

        if (!searchModel.getTags().isEmpty()) {
            ArrayList<ChildRow> childRowsTags = new ArrayList<>();
            TagModel model;
            ParentRow parentRow;
            for (int i = 0; i < searchModel.getTags().size(); i++) {
                model = searchModel.getTags().get(i);
                Log.d(TAG, "TAG MODEL SEARCH: " + model.getName());
                childRowsTags.add(new ChildRow(R.drawable.pound, model));
            }
            parentRow = new ParentRow("Tags", childRowsTags);
            mCategoriesList.add(parentRow);
        }*/

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
