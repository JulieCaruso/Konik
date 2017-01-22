package com.kapouter.konik.favorites;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kapouter.konik.R;
import com.kapouter.konik.home.Book;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter {

    private List<Book> mItems = new ArrayList<>();
    private String mUid;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(new FavoriteItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Book book = mItems.get(position);
        final FavoriteViewHolder viewHolder = (FavoriteViewHolder) holder;
        viewHolder.mItem.setTitle(book.getTitle());
        viewHolder.mItem.setAuthor(book.getAuthor());
        viewHolder.mItem.mAmazonUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent amazonIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getAmazonUrl()));
                if (amazonIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(amazonIntent);
                }
            }
        });
        viewHolder.mItem.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritesManager.deleteFavorite(mUid, book.getKey());
                mItems.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Book> favorites) {
        mItems.clear();
        if (favorites != null) mItems.addAll(favorites);
        notifyDataSetChanged();
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    private class FavoriteItem extends LinearLayout {
        private TextView mTitle;
        private TextView mAuthor;
        private ImageView mAmazonUrl;
        private ImageView mDelete;

        public FavoriteItem(final Context context) {
            super(context);
            View.inflate(context, R.layout.favorite_item, this);
            mTitle = (TextView) findViewById(R.id.favorite_item_title);
            mAuthor = (TextView) findViewById(R.id.favorite_item_author);
            mAmazonUrl = (ImageView) findViewById(R.id.favorite_item_amazon_url);
            mDelete = (ImageView) findViewById(R.id.favorite_item_delete);
        }

        private void setTitle(String title) {
            mTitle.setText(title);
        }

        private void setAuthor(String author) {
            mAuthor.setText(author);
        }
    }

    private class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final FavoritesAdapter.FavoriteItem mItem;

        public FavoriteViewHolder(FavoritesAdapter.FavoriteItem itemView) {
            super(itemView);
            mItem = itemView;
        }
    }
}
