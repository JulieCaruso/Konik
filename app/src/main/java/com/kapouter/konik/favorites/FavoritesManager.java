package com.kapouter.konik.favorites;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapouter.konik.home.Book;

import java.util.HashMap;
import java.util.Map;

public class FavoritesManager {

    public static void getFavorites(String uid, ValueEventListener listener) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(uid);
        db.addListenerForSingleValueEvent(listener);
    }

    public static void addFavorite(String uid, Book book) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> bookFav = new HashMap<>();
        bookFav.put("title", book.getTitle());
        bookFav.put("author", book.getAuthor());
        bookFav.put("amazon_url", book.getmAmazonUrl());
        db.child("users").child(uid).child("favorites").push().setValue(bookFav);
    }

}
