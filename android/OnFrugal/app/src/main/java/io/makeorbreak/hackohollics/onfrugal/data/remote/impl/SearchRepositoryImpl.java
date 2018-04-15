package io.makeorbreak.hackohollics.onfrugal.data.remote.impl;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.makeorbreak.hackohollics.onfrugal.data.remote.base.AbstractRepository;
import io.makeorbreak.hackohollics.onfrugal.data.remote.base.ServerUrl;
import io.makeorbreak.hackohollics.onfrugal.data.remote.exceptions.RemoteDataException;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Search;
import io.makeorbreak.hackohollics.onfrugal.domain.model.User;
import io.makeorbreak.hackohollics.onfrugal.domain.repository.SearchRepository;

public class SearchRepositoryImpl extends AbstractRepository implements SearchRepository {

    public static final String TAG = SearchRepositoryImpl.class.getSimpleName();
    private static final String API_SEARCH_URL = ServerUrl.getUrl() + ServerUrl.API + ServerUrl.SEARCH + "?query=";
    private static final String JSON_OFFER = "offer";
    private static final String JSON_USER = "user";

    public SearchRepositoryImpl(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public SearchRepositoryImpl(Context context) {
        super(context);
    }

    @Override
    public Search search(String searchQuery, double lat, double lng) {
        // Instantiate the RequestQueue.
        RequestQueue queue = mRequestQueue;

        String url = API_SEARCH_URL;
        if (lat != 0.0 && lng != 0.0) {
            url = url.concat(searchQuery);
        } else url = url.concat(searchQuery + "&" + lat + "&" + lng);

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
    Search getSearchModelsFromRequest(RequestFuture<JSONObject> future) throws InterruptedException, ExecutionException, TimeoutException, JSONException {
        JSONObject response = future.get(ServerUrl.TIMEOUT, ServerUrl.TIMEOUT_TIME_UNIT); // this will block
        System.out.println(TAG + ": " + String.valueOf(response));


        Search searchModel = new Search();

        searchModel.setOffers(getArrayListOfPoiModels(response.getJSONArray(JSON_OFFER)));
        searchModel.setUsers(getArrayListOfRouteModels(response.getJSONArray(JSON_USER)));

        return searchModel;
    }

    private ArrayList<Offer> getArrayListOfPoiModels(JSONArray jsonArray) throws JSONException {
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

    private Offer toOffer(JSONObject object) throws JSONException {
        String uid = object.getString("uid");
        String name = object.getString("name");
        User host = toUser(object.getJSONObject("host"));
        String description = object.getString("description");
        Date endDate = new Date(object.getString("endDate"));
        String location = object.getString("location");
        int spots = Integer.parseInt(object.getString("spots"));
        float price = Float.parseFloat(object.getString("price"));

        return new Offer(uid,
                name,
                host,
                description,
                endDate,
                location,
                spots,
                price);

    }

    private ArrayList<User> getArrayListOfRouteModels(JSONArray jsonArray) throws JSONException {
        ArrayList<User> models = new ArrayList<>();
        User model;
        JSONObject object;

        System.out.println(TAG + ": " + String.valueOf(jsonArray));

        for (int i = 0; i < jsonArray.length(); i++) {
            object = jsonArray.getJSONObject(i);
            model = toUser(object);

            models.add(model);
        }

        return models;
    }

    private User toUser(JSONObject object) throws JSONException{
        String uid = object.getString("uid");
        String name = object.getString("name");

        String email = "";
        String phoneNumber = "";

        if(object.has("email")) email = object.getString("email");
        if(object.has("phoneNumber")) phoneNumber = object.getString("phoneNumber");

        return new User(uid,name,email,phoneNumber);

    }

    @NotNull
    @Override
    public Search search(@NotNull String query) {
        return search(query,0,0);
    }
}