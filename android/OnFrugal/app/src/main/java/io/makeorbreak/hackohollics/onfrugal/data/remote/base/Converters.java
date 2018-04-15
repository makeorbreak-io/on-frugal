package io.makeorbreak.hackohollics.onfrugal.data.remote.base;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.User;

public class Converters {

    public static Offer toOffer(JSONObject object) throws JSONException{
        try{


            String uid = object.getString("_id");
            String name = object.getString("name");
            User host = toUser(object.getJSONObject("host"));
            String description = object.getString("description");
            SimpleDateFormat sdf;
            Date endDate;
            try{
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                endDate = sdf.parse(object.getString("time"));
            }catch (ParseException e){
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                endDate = sdf.parse(object.getString("time"));
            }
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
        }catch (ParseException e1){
            return null;
        }


    }

    public static User toUser(JSONObject object) throws JSONException{
        String uid = object.getString("idFirebase");
        String name = object.getString("name");

        String email = "";
        String phoneNumber = "";

        if(object.has("email")) email = object.getString("email");
        if(object.has("phoneNumber")) phoneNumber = object.getString("phoneNumber");

        return new User(uid,name,email,phoneNumber);

    }
}
