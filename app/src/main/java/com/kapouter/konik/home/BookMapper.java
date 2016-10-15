package com.kapouter.konik.home;

import android.util.Patterns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookMapper {

    private static final String BOOK_ERRORS = "errors";
    private static final String BOOK_RESULTS = "results";
    private static final String BOOK_DISPLAY_NAME = "display_name"; // of list
    private static final String BOOK_RANK = "rank";
    private static final String BOOK_AMAZON_URL = "amazon_product_url";
    private static final String BOOK_DETAILS = "book_details";
    private static final String BOOK_DETAIL_TITLE = "title";
    private static final String BOOK_DETAIL_DESCRIPTION = "description";
    private static final String BOOK_DETAIL_CONTRIBUTOR = "contributor";
    private static final String BOOK_DETAIL_AUTHOR = "author";
    private static final String BOOK_DETAIL_PUBLISHER = "publisher";
    private static final String BOOK_REVIEWS = "reviews";
    private static final String BOOK_REVIEW_URL = "book_review_link";

    public static boolean mapError(JSONObject result) {
        return result.has(BOOK_ERRORS);
    }

    public static List<Book> mapBooks(JSONObject result) {
        List<Book> books = new ArrayList<>();
        try {
            JSONArray results = result.getJSONArray(BOOK_RESULTS);
            for (int i = 0; i < results.length(); i++) {
                JSONObject bookJson = results.getJSONObject(i);
                Book book = mapBook(bookJson);
                if (book != null) books.add(book);
            }
        } catch (JSONException e) {
            return new ArrayList<>();
        }
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                if (book1.getRank() == book2.getRank()) return 0;
                return book1.getRank() < book2.getRank() ? -1 : 1;
            }
        });
        return books;
    }

    private static Book mapBook(JSONObject result) {
        try {
            int rank = result.getInt(BOOK_RANK);
            String amazonUrl = result.getString(BOOK_AMAZON_URL);
            JSONObject bookDetails = result.getJSONArray(BOOK_DETAILS).getJSONObject(0);
            String title = bookDetails.getString(BOOK_DETAIL_TITLE);
            String description = bookDetails.getString(BOOK_DETAIL_DESCRIPTION);
            String contributor = bookDetails.getString(BOOK_DETAIL_CONTRIBUTOR);
            String author = bookDetails.getString(BOOK_DETAIL_AUTHOR);
            String publisher = bookDetails.getString(BOOK_DETAIL_PUBLISHER);
            Book book = new Book(title, description, contributor, author, publisher, rank, amazonUrl);
            JSONArray reviews = result.getJSONArray(BOOK_REVIEWS);
            mapReviews(book, reviews);
            return book;
        } catch (JSONException e) {
            return null;
        }
    }

    private static void mapReviews(Book book, JSONArray reviews) {
        for (int i = 0; i < reviews.length(); i++) {
            try {
                JSONObject review = reviews.getJSONObject(i);
                String reviewUrl = review.getString(BOOK_REVIEW_URL);
                if (Patterns.WEB_URL.matcher(reviewUrl).matches()) book.addReview(reviewUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
