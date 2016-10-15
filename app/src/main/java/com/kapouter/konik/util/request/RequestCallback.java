package com.kapouter.konik.util.request;

import com.kapouter.konik.home.Book;

import java.util.List;

public interface RequestCallback {

    void newData();

    void noChange();

    void error();
}
