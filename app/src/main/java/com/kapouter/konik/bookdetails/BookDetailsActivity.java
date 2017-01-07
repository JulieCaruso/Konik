package com.kapouter.konik.bookdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapouter.konik.R;
import com.kapouter.konik.favorites.FavoritesManager;
import com.kapouter.konik.home.Book;
import com.kapouter.konik.home.BookManager;
import com.koushikdutta.ion.Ion;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "BookDetailsActivity.EXTRA_BOOK_ID";

    private FirebaseAuth mAuth;

    private ImageView mImage;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDescription;

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity);

        mImage = (ImageView) findViewById(R.id.book_details_image);
        mTitle = (TextView) findViewById(R.id.book_details_title);
        mAuthor = (TextView) findViewById(R.id.book_details_author);
        mDescription = (TextView) findViewById(R.id.book_details_description);

        mAuth = FirebaseAuth.getInstance();

        Bundle args = getIntent().getExtras();
        if (args != null) {
            Integer bookId = args.getInt(EXTRA_BOOK_ID);
            if (bookId != null) {
                mBook = BookManager.getCachedBook(bookId);
                setView(mBook);
            }
        }

        findViewById(R.id.book_details_add_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    if (mBook != null) FavoritesManager.addFavorite(user.getUid(), mBook);
                }
            }
        });

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
