package com.kapouter.konik.favorites;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapouter.konik.home.Book;
import com.kapouter.konik.util.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesManager {

    private static final String CACHED_FAVORITES = "FavoritesManager.CACHED_FAVORITES";

    public static void setCachedFavorites(Map<String, Map<String, String>> favorites) {
        Cache.set(CACHED_FAVORITES, mapFavorites(favorites));
    }

    public static List<Book> getCachedFavorites() {
        return ((List<Book>) Cache.get(CACHED_FAVORITES));
    }

    public static void getFavorites(String uid, ValueEventListener listener) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(uid);
        db.addListenerForSingleValueEvent(listener);
    }

    public static void addFavorite(String uid, Book book) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> bookFav = new HashMap<>();
        bookFav.put("title", book.getTitle());
        bookFav.put("author", book.getAuthor());
        bookFav.put("amazon_url", book.getAmazonUrl());
        db.child("users").child(uid).child("favorites").push().setValue(bookFav);
    }

    public static void deleteFavorite(String uid, String key) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(uid).child("favorites").child(key).removeValue();
    }

    private static List<Book> mapFavorites(Map<String, Map<String, String>> favoritesDb) {
        List<Book> favorites = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : favoritesDb.entrySet()) {
            Map<String, String> favoriteDb = entry.getValue();
            String title = favoriteDb.get("title");
            String author = favoriteDb.get("author");
            String amazonUrl = favoriteDb.get("amazon_url");
            Book book = new Book(title, author, amazonUrl, entry.getKey());
            favorites.add(book);
        }
        return favorites;
    }

}
