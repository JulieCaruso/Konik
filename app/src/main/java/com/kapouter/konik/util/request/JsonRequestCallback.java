package com.kapouter.konik.util.request;

import org.json.JSONObject;

public interface JsonRequestCallback {

    void newData(JSONObject result);

    void noChange(JSONObject result);

    void error(Exception error);
}
