package com.tik.tiktok;


import com.google.gson.Gson;

/**
 * Created by Vishal Sojitra on 4/22/2016.
 */
public class GsonUtils {
    private static GsonUtils sInstance;
    private Gson mGson;

    private GsonUtils() {
//  mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
//    .enableComplexMapKeySerialization()
//    .create();
        mGson = new Gson();
    }

    public static GsonUtils getInstance() {
        if (sInstance == null) {
            sInstance = new GsonUtils();
        }
        return sInstance;
    }

    public String toJson(Object object) {
        return mGson.toJson(object);
    }

    public Gson getGson() {
        return mGson;
    }


}
