package epicodus.booktracker.ui;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

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
    @Bind(R.id.descriptionTextView) TextView mDescriptionLabel;
    @Bind(R.id.pageCountTextView) TextView mPageCountLabel;
    @Bind(R.id.saveBookButton) Button mSaveBookButton;
    @Bind(R.id.editBookButton) Button mEditBookButton;
    @Bind(R.id.readingProgressRelativeLayout) RelativeLayout mReadingProgressRelativeLayout;

    @Bind(R.id.currentPageTextView) TextView mCurrentPageTextView;
    @Bind(R.id.avgPageTextView) TextView mAvgPageTextView;
    @Bind(R.id.endDateTextView) TextView mEndDateTextView;
    @Bind(R.id.startDateTextView) TextView mStartDateTextView;

    private SharedPreferences mSharedPreferences;
    private Book mBook;

    private ArrayList<Book> mBooks;
    private Integer mPosition;
    private String mSource;


    public static BookDetailFragment newInstance(ArrayList<Book> books, Integer position, String source) {
        BookDetailFragment bookDetailFragment = new BookDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_JOBS, Parcels.wrap(books));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        bookDetailFragment.setArguments(args);
        return bookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooks = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_JOBS));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mBook = mBooks.get(mPosition);

        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ButterKnife.bind(this, view);
        mSaveBookButton.setOnClickListener(this);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveBookButton.setVisibility(View.GONE);
            mDescriptionLabel.setVisibility(View.GONE);
            mEditBookButton.setOnClickListener(this);
            //might not be right

        } else {
            mSaveBookButton.setOnClickListener(this);
            mReadingProgressRelativeLayout.setVisibility(View.GONE);
            mEditBookButton.setVisibility(View.GONE);
            mCurrentPageTextView.setVisibility(View.GONE);
            mAvgPageTextView.setVisibility(View.GONE);
            mStartDateTextView.setVisibility(View.GONE);
            mEndDateTextView.setVisibility(View.GONE);
        }

            final String imageUrl = mBook.getImage();

        if (TextUtils.isEmpty(imageUrl)) {
                           mImageLabel.setImageResource(R.drawable.noimage);
        } else {
            Picasso.with(view.getContext()).load(mBook.getImage()).into(mImageLabel);

        }
        mAuthorLabel.setText(mBook.getAuthor());
        mBookNameLabel.setText(mBook.getTitle());
        mDescriptionLabel.setText(mBook.getDescription());
        //mPageCountLabel.setText(mBook.getPageCount());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mSaveBookButton) {
            String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
            Firebase userBooksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid);
            Firebase pushRef = userBooksFirebaseRef.push();
            String bookPushId = pushRef.getKey();
            mBook.setPushId(bookPushId);
            pushRef.setValue(mBook);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
        if (view == mEditBookButton) {
            showEditBookDialog();
        }
    }

    protected void showEditBookDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);

        final EditText currentPageEditText = (EditText)dialog.findViewById(R.id.currentPageEditText);
        Button confirmEditBookButton = (Button)dialog.findViewById(R.id.confirmEditBookButton);

        confirmEditBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPageTextView.setText(currentPageEditText.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}



