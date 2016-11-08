package com.kapouter.konik.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kapouter.konik.R;
import com.kapouter.konik.bookdetails.BookDetailsActivity;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter {

    private List<Book> mItems = new ArrayList<>();

    public void setItems(List<Book> items) {
        mItems.clear();
        if (items != null) mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookViewHolder(new BookItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Book book = mItems.get(position);
        final BookViewHolder viewHolder = (BookViewHolder) holder;
        viewHolder.mItem.setImage(book.getImageUrl());
        viewHolder.mItem.setTitle(book.getTitle());
        viewHolder.mItem.setAuthor(book.getAuthor());
        viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookDetails = new Intent(viewHolder.mItem.getContext(), BookDetailsActivity.class);
                bookDetails.putExtra(BookDetailsActivity.EXTRA_BOOK_ID, position);
                viewHolder.mItem.getContext().startActivity(bookDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class BookItem extends LinearLayout {

        private ImageView mImage;
        private TextView mTitle;
        private TextView mAuthor;

        public BookItem(final Context context) {
            super(context);
            View.inflate(context, R.layout.book_item, this);
            mImage = (ImageView) findViewById(R.id.book_item_image);
            mTitle = (TextView) findViewById(R.id.book_item_title);
            mAuthor = (TextView) findViewById(R.id.book_item_author);
        }

        private void setImage(String imageUrl) {
            if (Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Ion.with(mImage).load(imageUrl);
            }
        }

        private void setTitle(String title) {
            mTitle.setText(title);
        }

        private void setAuthor(String author) {
            mAuthor.setText(author);
        }


    }

    private class BookViewHolder extends RecyclerView.ViewHolder {
        private final BookItem mItem;

        public BookViewHolder(BookItem itemView) {
            super(itemView);
            mItem = itemView;
        }
    }
}
