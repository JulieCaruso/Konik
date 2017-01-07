package com.kapouter.konik.util.request;

import com.kapouter.konik.BuildConfig;
import com.kapouter.konik.app.App;
import com.kapouter.konik.util.Cache;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NYTRequest {

    private static final String CACHED_RESPONSES = "NYTRequest.CACHED_RESPONSES";

    private String mRequestUrl;
    private Map<String, List<String>> mQueryParameters;
    private JsonRequestCallback mCallback;

    private NYTRequest(String url) {
        mRequestUrl = url;
        mQueryParameters = new HashMap<>();
    }

    public NYTRequest setQueryParam(String name, String value) {
        mQueryParameters.put(name, Collections.singletonList(value));
        return this;
    }

    public JsonRequestCallback getCallback() {
        return mCallback;
    }

    public NYTRequest setCallback(JsonRequestCallback callback) {
        mCallback = callback;
        return this;
    }

    public static NYTRequest with() {
        return new NYTRequest(BuildConfig.NYT_API_URL + "/combined-print-and-e-book-fiction" + ".json");
    }

    public static NYTRequest with(int urlRes) {
        String url = App.getInstance().getApplicationContext().getString(urlRes);
        return new NYTRequest(BuildConfig.NYT_API_URL + url + ".json");
    }

    public void get(JsonRequestCallback callback) {
        setQueryParam("api-key", BuildConfig.NYT_API_KEY);
        setCallback(callback);
        Ion.with(App.getInstance())
                .load(mRequestUrl)
                .noCache()
                .addQueries(mQueryParameters)
                .asString()
                .withResponse()
                .setCallback(mRequestCallback);
    }

    private FutureCallback<Response<String>> mRequestCallback = new FutureCallback<Response<String>>() {
        @Override
        public void onCompleted(Exception error, Response<String> response) {
            if (error != null) {
                mCallback.error(error);
            } else {
                String result = response.getResult();
                try {
                    // Build Jason
                    JSONObject jason = new JSONObject(result);
                    // Cache
                    Map<String, String> responses = Cache.get(CACHED_RESPONSES);
                    // Init cached responses if null
                    if (responses == null) {
                        responses = new HashMap<>();
                        Cache.set(CACHED_RESPONSES, responses);
                    }
                    if (result.equals(responses.get(mRequestUrl))) {
                        mCallback.noChange(jason);
                    } else {
                        responses.put(mRequestUrl, result);
                        mCallback.newData(jason);
                    }
                } catch (JSONException exception) {
                    mCallback.error(exception);
                }
            }
        }
    };
}
