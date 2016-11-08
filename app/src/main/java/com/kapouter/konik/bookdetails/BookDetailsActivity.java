package com.kapouter.konik.bookdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapouter.konik.R;
import com.kapouter.konik.home.Book;
import com.kapouter.konik.home.BookManager;
import com.koushikdutta.ion.Ion;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "BookDetailsActivity.EXTRA_BOOK_ID";

    private ImageView mImage;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity);

        mImage = (ImageView) findViewById(R.id.book_details_image);
        mTitle = (TextView) findViewById(R.id.book_details_title);
        mAuthor = (TextView) findViewById(R.id.book_details_author);
        mDescription = (TextView) findViewById(R.id.book_details_description);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            Integer bookId = args.getInt(EXTRA_BOOK_ID);
            if (bookId != null)
                setView(BookManager.getCachedBook(bookId));
        }

    }

    private void setView(Book book) {
        if (Patterns.WEB_URL.matcher(book.getImageUrl()).matches()) {
            Ion.with(mImage).load(book.getImageUrl());
        }
        mTitle.setText(book.getTitle());
        mAuthor.setText(book.getAuthor());
        mDescription.setText(book.getDescription());
    }
}
