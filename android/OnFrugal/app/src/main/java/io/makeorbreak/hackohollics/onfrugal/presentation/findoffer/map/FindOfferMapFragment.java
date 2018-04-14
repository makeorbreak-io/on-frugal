package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;

import java.util.ArrayList;
import java.util.Objects;

public class FindOfferMapFragment extends LocationBasedMapFragment implements
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMapLoadedCallback {

    private static final String TAG = FindOfferMapFragment.class.getSimpleName();
    private static final LatLng PORTO_LAT_LNG = new LatLng(41.1485647, -8.6119707);

    private boolean mFirstZoomFlag = false;

    //Variables needed to keep status of the last call in order to avoid overcalling the onCameraMove function
    private static int CAMERA_MOVE_REACT_THRESHOLD_MS = 500;
    private long mLastCallMs = Long.MIN_VALUE;
    private LatLngBounds mCurrentCameraBounds;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance) {
        View mRootView = super.onCreateView(layoutInflater, viewGroup, savedInstance);

        getMapAsync(this);

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mContext = getContext();

        return mRootView;
    }

    @Override
    protected void updateCurrentLocation(LatLng latLng) {
        //move map camera
        if (!mFirstZoomFlag && mGoogleMap != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
            mFirstZoomFlag = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        mGoogleMap.setOnCameraMoveListener(this);
        // Initialized for onCameraMoveListener to use
        mCurrentCameraBounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;

        // Set flag so that it that the map starts on the current location
        mFirstZoomFlag = true;

        mGoogleMap.setOnMapLoadedCallback(this);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PORTO_LAT_LNG, ZOOM));
    }

    @Override
    public void onCameraMove() {
        LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;

        // Check whether the camera changes report the same boundaries (?!), yes, it happens
        if (mCurrentCameraBounds.northeast.latitude == bounds.northeast.latitude
                && mCurrentCameraBounds.northeast.longitude == bounds.northeast.longitude
                && mCurrentCameraBounds.southwest.latitude == bounds.southwest.latitude
                && mCurrentCameraBounds.southwest.longitude == bounds.southwest.longitude) {
            return;
        }

        final long snap = System.currentTimeMillis();
        if (mLastCallMs + CAMERA_MOVE_REACT_THRESHOLD_MS > snap) {
            mLastCallMs = snap;
            return;
        }

        //Store cache fields
        mLastCallMs = snap;
        mCurrentCameraBounds = bounds;

        //Fetch data
        fetchOffersOnCameraMove(bounds);
    }

    private void fetchOffersOnCameraMove(LatLngBounds bounds) {
        LatLng northeast = bounds.northeast;
        LatLng southwest = bounds.southwest;

        Log.d(TAG, bounds.toString());

        double minLat, maxLat, minLng, maxLng;
        minLat = southwest.latitude;
        maxLat = northeast.latitude;
        minLng = southwest.longitude;
        maxLng = northeast.longitude;

        /*OffersInteractor.CallBack callBack = new OffersInteractor.CallBack<ArrayList<Offers>>() {
            @Override
            public void onSuccess(ArrayList<Offer> offers) {
                Log.d(TAG, "onSuccess(): nPois- "+offers.size());
                onPoiFetch(offers);
            }

            @Override
            public void onNetworkError() {
                if (isAdded()) {
                    Toast.makeText(mContext, R.string.error_connection, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String code, String message) {

                if (isAdded()) {
                    if(message == null || message.length() == 0) message = getResources().getString(R.string.error_fetch);
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            }
        };

        OffersMapInteractor = new OffersMapInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                callBack,
                new CloudOffersRepository(mContext),
                minLat, maxLat, minLng, maxLng
        );
        OffersMapInteractor.execute();*/

        Log.d(TAG, "Lat: " + minLat + " - " + maxLat + " ; Lng: " + minLng + " - " + maxLng);
    }

    private void onOffersFetch(ArrayList<Offer> offers) {
//        offersMapMarker.addMarkers(offers);
    }

    public LatLng getPosition(){
        return  new LatLng(mLatitude, mLongitude);
    }

    @Override
    public void onMapLoaded() {
        fetchOffersOnCameraMove(mGoogleMap.getProjection().getVisibleRegion().latLngBounds);
    }
}
