package com.kapouter.konik.home;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private int mRank;
    private String mTitle;
    private String mDescription;
    private String mContributor;
    private String mAuthor;
    private String mPublisher;
    private String mImageUrl;
    private int mImageWidth;
    private int mImageHeight;
    private String mAmazonUrl;
    private String mReviewUrl;

    public Book(int rank, String title, String description, String contributor, String author, String publisher, String imageUrl, int imageWidth, int imageHeight, String amazonUrl, String reviewUrl) {
        mRank = rank;
        mTitle = title;
        mDescription = description;
        mContributor = contributor;
        mAuthor = author;
        mPublisher = publisher;
        mImageUrl = imageUrl;
        mImageWidth = imageWidth;
        mImageHeight = imageHeight;
        mAmazonUrl = amazonUrl;
        mReviewUrl = reviewUrl;
    }

    public int getRank() {
        return mRank;
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

    public String getImageUrl() {
        return mImageUrl;
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    public String getmAmazonUrl() {
        return mAmazonUrl;
    }

    public String getReviewUrl() {
        return mReviewUrl;
    }
}
