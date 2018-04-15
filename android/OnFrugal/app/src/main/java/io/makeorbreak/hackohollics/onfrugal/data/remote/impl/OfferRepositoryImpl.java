package io.makeorbreak.hackohollics.onfrugal.data.remote.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.makeorbreak.hackohollics.onfrugal.data.remote.base.AbstractRepository;
import io.makeorbreak.hackohollics.onfrugal.data.remote.base.ServerUrl;
import io.makeorbreak.hackohollics.onfrugal.data.remote.exceptions.RemoteDataException;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.User;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.OfferRepository;

import static io.makeorbreak.hackohollics.onfrugal.data.remote.base.Converters.toOffer;

public class OfferRepositoryImpl extends AbstractRepository implements OfferRepository {



    private static final String TAG = SearchRepositoryImpl.class.getSimpleName();
    private static final String API_SEARCH_URL = ServerUrl.getUrl() + ServerUrl.API + "/offer/";
    private static final String JSON_OFFER = "offers";

    public OfferRepositoryImpl(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public OfferRepositoryImpl(Context context) {
        super(context);
    }

    @NotNull
    @Override
    public Offer getOffer(@NotNull String uid) {
        return null;
    }

    @NotNull
    @Override
    public List<Offer> getOffers(double lat, double lng) {
        // Instantiate the RequestQueue.
        RequestQueue queue = mRequestQueue;

        String url = API_SEARCH_URL + "location/";

        url = url.concat(lat + "/" + lng);

        Log.d(TAG, url);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, future, future);
        queue.add(request);
        try{
            return getSearchModelsFromRequest(future);
        } catch (InterruptedException | ExecutionException | JSONException | TimeoutException e) {
            try {
                handleError(e);
            } catch (RemoteDataException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    @Nullable
    List<Offer> getSearchModelsFromRequest(RequestFuture<JSONObject> future) throws InterruptedException, ExecutionException, TimeoutException, JSONException {
        JSONObject response = future.get(ServerUrl.TIMEOUT, ServerUrl.TIMEOUT_TIME_UNIT); // this will block
        System.out.println(TAG + ": " + String.valueOf(response));


        return getArrayListOfOffers(response.getJSONArray(JSON_OFFER));
    }

    private ArrayList<Offer> getArrayListOfOffers(JSONArray jsonArray) throws JSONException {
        ArrayList<Offer> models = new ArrayList<>();
        Offer model;
        JSONObject object;

        System.out.println(TAG + ": " + String.valueOf(jsonArray));

        for (int i = 0; i < jsonArray.length(); i++) {
            object = jsonArray.getJSONObject(i);
            model = toOffer(object);
            models.add(model);
        }

        return models;
    }



}
