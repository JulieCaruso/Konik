package com.kapouter.konik.home;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String mTitle;
    private String mDescription;
    private String mContributor;
    private String mAuthor;
    private String mPublisher;
    private List<String> mReviews;
    private int mRank;
    private String mAmazonUrl;

    public Book(String title, String description, String contributor, String author, String publisher, int rank, String amazonUrl) {
        mTitle = title;
        mDescription = description;
        mContributor = contributor;
        mAuthor = author;
        mPublisher = publisher;
        mReviews = new ArrayList<>();
        mRank = rank;
        mAmazonUrl = amazonUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getContributor() {
        return mContributor;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public List<String> getReviews() {
        return mReviews;
    }

    public void addReview(String reviewUrl) {
        mReviews.add(reviewUrl);
    }

    public int getRank() {
        return mRank;
    }

    public String getmAmazonUrl() {
        return mAmazonUrl;
    }
}
