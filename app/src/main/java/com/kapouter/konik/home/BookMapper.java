package com.kapouter.konik.home;

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
    private static final String BOOK_BOOKS = "books";
    private static final String BOOK_RANK = "rank";
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_DESCRIPTION = "description";
    private static final String BOOK_CONTRIBUTOR = "contributor";
    private static final String BOOK_AUTHOR = "author";
    private static final String BOOK_PUBLISHER = "publisher";
    private static final String BOOK_IMAGE_URL = "book_image";
    private static final String BOOK_IMAGE_WIDTH = "book_image_width";
    private static final String BOOK_IMAGE_HEIGHT = "book_image_height";
    private static final String BOOK_AMAZON_URL = "amazon_product_url";
    private static final String BOOK_REVIEW_URL = "book_review_link";

    public static boolean mapError(JSONObject result) {
        return result.has(BOOK_ERRORS);
    }

    public static List<Book> mapBooks(JSONObject result) {
        List<Book> books = new ArrayList<>();
        try {
            JSONObject results = result.getJSONObject(BOOK_RESULTS);
            JSONArray booksJson = results.getJSONArray(BOOK_BOOKS);
            for (int i = 0; i < booksJson.length(); i++) {
                JSONObject bookJson = booksJson.getJSONObject(i);
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
            String title = result.getString(BOOK_TITLE);
            String description = result.getString(BOOK_DESCRIPTION);
            String contributor = result.getString(BOOK_CONTRIBUTOR);
            String author = result.getString(BOOK_AUTHOR);
            String publisher = result.getString(BOOK_PUBLISHER);
            String imageUrl = result.getString(BOOK_IMAGE_URL);
            int imageWidth = result.getInt(BOOK_IMAGE_WIDTH);
            int imageHeight = result.getInt(BOOK_IMAGE_HEIGHT);
            String amazonUrl = result.getString(BOOK_AMAZON_URL);
            String reviewUrl = result.getString(BOOK_REVIEW_URL);
            return new Book(rank, title, description, contributor, author, publisher, imageUrl, imageWidth, imageHeight, amazonUrl, reviewUrl);
        } catch (JSONException e) {
            return null;
        }
    }
}
