package com.kapouter.konik.home;

import com.kapouter.konik.util.Cache;
import com.kapouter.konik.util.request.JsonRequestCallback;
import com.kapouter.konik.util.request.NYTRequest;

import org.json.JSONObject;

import java.util.List;

public class BookManager {

    private static final String CACHED_BOOKS = "BookManager.CACHED_BOOKS";

    public static List<Book> getCachedBooks() {
        return Cache.get(CACHED_BOOKS);
    }

    public static void getList(String list, final com.kapouter.konik.util.request.RequestCallback callback) {
        NYTRequest.with()
                .setQueryParam("list", list)
                .get(new JsonRequestCallback() {
                    @Override
                    public void newData(JSONObject result) {
                        if (BookMapper.mapError(result)) error(new Exception("error"));
                        else {
                            Cache.set(CACHED_BOOKS, BookMapper.mapBooks(result));
                            callback.newData();
                        }
                    }

                    @Override
                    public void noChange(JSONObject result) {
                        callback.noChange();
                    }

                    @Override
                    public void error(Exception error) {
                        callback.error();
                    }
                });
    }

}
