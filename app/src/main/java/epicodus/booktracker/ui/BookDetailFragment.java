package epicodus.booktracker.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.bookImageView) ImageView mImageLabel;
    @Bind(R.id.authorTextView) TextView mAuthorLabel;
    @Bind(R.id.bookNameTextView) TextView mBookNameLabel;
    @Bind(R.id.categoryTextView) TextView mCategoryLabel;
    @Bind(R.id.descriptionTextView) TextView mDescriptionLabel;
    @Bind(R.id.pageCountTextView) TextView mPageCountLabel;
    @Bind(R.id.saveBookButton) Button mSaveBookButton;

    private Book mBook;



    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", Parcels.wrap(book));
        bookDetailFragment.setArguments(args);
        return bookDetailFragment;
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBook = Parcels.unwrap(getArguments().getParcelable("book"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ButterKnife.bind(this, view);
        mSaveBookButton.setOnClickListener(this);

        Picasso.with(view.getContext()).load(mBook.getImage()).into(mImageLabel);
        mAuthorLabel.setText(mBook.getAuthor());
        mBookNameLabel.setText(mBook.getTitle());
        mDescriptionLabel.setText(mBook.getDescription());
//        mPageCountLabel.setText(mBook.getPageCount());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mSaveBookButton) {
            Firebase ref = new Firebase(Constants.FIREBASE_URL_BOOKS);
            ref.push().setValue(mBook);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}



