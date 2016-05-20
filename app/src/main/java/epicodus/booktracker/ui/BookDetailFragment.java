package epicodus.booktracker.ui;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;


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
    @Bind(R.id.categoryTextView) TextView mCategoryTextView;
    @Bind(R.id.publishedDateTextView) TextView mPublishedDateTextView;
    @Bind(R.id.descriptionTextView) TextView mDescriptionLabel;
    @Bind(R.id.pageCountTextView) TextView mPageCountLabel;
    @Bind(R.id.saveBookButton) Button mSaveBookButton;
    @Bind(R.id.editBookButton) Button mEditBookButton;
   // @Bind(R.id.readingProgressRelativeLayout) RelativeLayout mReadingProgressRelativeLayout;

    public static final String TAG = BookDetailFragment.class.getSimpleName();

    @Bind(R.id.previewButton) Button mPreviewButton;

    @Bind(R.id.currentPageTextView) TextView mCurrentPageTextView;
    @Bind(R.id.avgPageTextView) TextView mAvgPageTextView;
    @Bind(R.id.endDateTextView) TextView mEndDateTextView;
    @Bind(R.id.startDateTextView) TextView mStartDateTextView;
    @Bind(R.id.startReadingButton) Button mStartReadingButton;
    @Bind(R.id.finishReadingButton) Button mFinishReadingButton;
    @Bind(R.id.avgPagesLabelTextView) TextView mAvgPagesLabelTextView;
    @Bind(R.id.startDateLabelTextView) TextView mStartDateLabelTextView;
    @Bind(R.id.endDateLabelTextView) TextView mEndDateLabelTextView;
    @Bind(R.id.totalPagesReadTextView) TextView mTotalPagesReadTextView;

    private SharedPreferences mSharedPreferences;
    private Book mBook;

    private ArrayList<Book> mBooks;
    private Integer mPosition;
    private String mSource;

    public static BookDetailFragment newInstance(ArrayList<Book> books, Integer position, String source) {
        BookDetailFragment bookDetailFragment = new BookDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_BOOKS, Parcels.wrap(books));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        bookDetailFragment.setArguments(args);
        return bookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooks = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_BOOKS));
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
        mPreviewButton.setOnClickListener(this);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveBookButton.setVisibility(View.INVISIBLE);
            mPageCountLabel.setVisibility(View.INVISIBLE);
            mPublishedDateTextView.setVisibility(View.INVISIBLE);
            mCategoryTextView.setVisibility(View.INVISIBLE);
            mDescriptionLabel.setVisibility(View.INVISIBLE);
            mFinishReadingButton.setVisibility(View.INVISIBLE);
            mStartDateLabelTextView.setVisibility(View.INVISIBLE);
            mEndDateLabelTextView.setVisibility(View.INVISIBLE);
            mTotalPagesReadTextView.setVisibility(View.VISIBLE);
            mTotalPagesReadTextView.setText("Total Pages Read");
            if (mBook.getStartDate() != null) {
                Date currentStateDate = mBook.getStartDate();
                String startDateString = new SimpleDateFormat("MM/dd/yyyy").format(currentStateDate);
                mStartDateTextView.setText("" + startDateString);
                mStartReadingButton.setVisibility(View.INVISIBLE);
                mStartDateLabelTextView.setVisibility(View.VISIBLE);
                mFinishReadingButton.setVisibility(View.VISIBLE);
                if (mBook.getEndDate() != null) {
                    mFinishReadingButton.setVisibility(View.INVISIBLE);
                    mEditBookButton.setVisibility(View.INVISIBLE);
                    mEndDateLabelTextView.setVisibility(View.VISIBLE);
                    Date finishStateDate = mBook.getEndDate();
                    String endDateString = new SimpleDateFormat("MM/dd/yyyy").format(finishStateDate);
                    mEndDateTextView.setText("- " + endDateString);
                }
            } else {
                mStartDateTextView.setText("");
                mEndDateTextView.setVisibility(View.INVISIBLE);
                mEditBookButton.setVisibility(View.INVISIBLE);
            }

            mStartReadingButton.setOnClickListener(this);
            mFinishReadingButton.setOnClickListener(this);
            mEditBookButton.setOnClickListener(this);
            mPreviewButton.setVisibility(View.INVISIBLE);
            mAvgPageTextView.setText(getAvgPagesPerDay());
            mCurrentPageTextView.setText(mBook.getCurrentPage() + "/" + mBook.getPageCount());

        } else {
            //mReadingProgressRelativeLayout.setVisibility(View.GONE);
            mEditBookButton.setVisibility(View.INVISIBLE);
            mCurrentPageTextView.setVisibility(View.INVISIBLE);
            mAvgPageTextView.setVisibility(View.INVISIBLE);
            mStartDateTextView.setVisibility(View.INVISIBLE);
            mEndDateTextView.setVisibility(View.INVISIBLE);
            mStartReadingButton.setVisibility(View.INVISIBLE);
            mFinishReadingButton.setVisibility(View.INVISIBLE);
            mAvgPagesLabelTextView.setVisibility(View.INVISIBLE);
            mStartDateLabelTextView.setVisibility(View.INVISIBLE);
            mEndDateLabelTextView.setVisibility(View.INVISIBLE);
            mTotalPagesReadTextView.setVisibility(View.INVISIBLE);
            mSaveBookButton.setOnClickListener(this);
        }

        final String imageUrl = mBook.getImage();

        if (TextUtils.isEmpty(imageUrl)) {
            Picasso.with(view.getContext()).load(R.drawable.noimage).resize(600, 800).into(mImageLabel);
//                           mImageLabel.setImageResource(R.drawable.noimage);
        } else {
            Picasso.with(view.getContext()).load(mBook.getImage()).resize(600, 800).into(mImageLabel);
        }

        mAuthorLabel.setText(mBook.getAuthor());
        mBookNameLabel.setText(mBook.getTitle());
        mDescriptionLabel.setText("Description:\n" + mBook.getDescription());
        mCategoryTextView.setText("Category: " + mBook.getCategory());
        mPageCountLabel.setText(mBook.getPageCount()+" Pages");
        mPublishedDateTextView.setText("Published: " + mBook.getPublishedDate());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBookButton:
                String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                Firebase userBooksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid);
                Firebase pushRef = userBooksFirebaseRef.push();
                String bookPushId = pushRef.getKey();
                mBook.setPushId(bookPushId);
                String bookId = Constants.KEY_BOOKID;
                bookId = pushRef.getKey();
                mBook.setPushId(bookId);
                pushRef.setValue(mBook);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.editBookButton:
                showEditBookDialog();
                break;
            case R.id.startReadingButton:
                mStartReadingButton.setVisibility(View.INVISIBLE);
                mFinishReadingButton.setVisibility(View.VISIBLE);
                mEditBookButton.setVisibility(View.VISIBLE);
                mStartDateLabelTextView.setVisibility(View.VISIBLE);
                Date newStartDate = new Date();
                mBook.setStartDate(newStartDate);
                Date currentStateDate = mBook.getStartDate();
                String startDateString = new SimpleDateFormat("MM/dd/yyyy").format(currentStateDate);
                mStartDateTextView.setText("" + startDateString);
                userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                String bookID = mSharedPreferences.getString(Constants.KEY_BOOKID, mBook.getPushId());
                Firebase booksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid).child(bookID);
                booksFirebaseRef.child("startDate").setValue(newStartDate);
                break;
            case R.id.finishReadingButton:
                mFinishReadingButton.setVisibility(View.GONE);
                mEditBookButton.setVisibility(View.INVISIBLE);
                mEndDateTextView.setVisibility(View.VISIBLE);
                mEndDateLabelTextView.setVisibility(View.VISIBLE);
                //sets endDate for finishedBook
                Date finishBookDate = new Date();
                mBook.setEndDate(finishBookDate);
                userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                bookID = mSharedPreferences.getString(Constants.KEY_BOOKID, mBook.getPushId());
                booksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid).child(bookID);
                booksFirebaseRef.child("endDate").setValue(finishBookDate);
                //sets currentPage
                mBook.setCurrentPage(mBook.getPageCount());
                int currentPage = mBook.getPageCount();
                Map<String, Object> bookMap = new HashMap<String, Object>();
                bookMap.put("currentPage", currentPage);
                booksFirebaseRef.updateChildren(bookMap);
                //sets avgPagesPerDay
                getAvgPagesPerDay();
                mCurrentPageTextView.setText(mBook.getCurrentPage()+"");

                Date finishStateDate = mBook.getEndDate();
                String endDateString = new SimpleDateFormat("MM/dd/yyyy").format(finishStateDate);
                mEndDateTextView.setText("- " + endDateString);
                break;
            case R.id.previewButton:
                String link = mBook.getPreviewLink();
                Log.d(TAG, link);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(webIntent);
            default:
                break;
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
                String currentPage = currentPageEditText.getText().toString();
                int currentPageInt = Integer.parseInt(currentPage);
                String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                String bookID = mSharedPreferences.getString(Constants.KEY_BOOKID, mBook.getPushId());
                Firebase booksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid).child(bookID);

                if (Integer.parseInt(currentPage) < mBook.getPageCount() && !(currentPage.equals("")) && (Integer.parseInt(currentPage) > 0)) {
                    mBook.setCurrentPage(currentPageInt);
                    mCurrentPageTextView.setText(mBook.getCurrentPage() + "/" + mBook.getPageCount());
                    Map<String, Object> bookMap = new HashMap<String, Object>();
                    bookMap.put("currentPage", currentPage);
                    booksFirebaseRef.updateChildren(bookMap);
                    avgPagesPerDayFirebase(Integer.parseInt(getAvgPagesPerDay()), booksFirebaseRef);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public String getAvgPagesPerDay() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String bookID = mSharedPreferences.getString(Constants.KEY_BOOKID, mBook.getPushId());
        Firebase booksFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS).child(userUid).child(bookID);

        int avgPages = 0;
        String avgPagesString = "0";

        Date date1 = new Date();
        if (mBook.getStartDate() != null) {
            long diff = Math.abs(date1.getTime() - mBook.getStartDate().getTime());
            long diffDays = diff / (24 * 60 * 60 * 1000);
            int diffDayInt = (int) diffDays;
            if (mBook.getCurrentPage() != 0 && diffDayInt != 0) {
                avgPages = mBook.getCurrentPage() / diffDayInt;
                avgPagesString = avgPages + "";
                avgPagesPerDayFirebase(avgPages, booksFirebaseRef);
                mAvgPageTextView.setText(avgPagesString);
            } else {
                avgPages = mBook.getCurrentPage();
                avgPagesString = avgPages + "";
                avgPagesPerDayFirebase(avgPages, booksFirebaseRef);
                mAvgPageTextView.setText(avgPages+"");
            }
        }
        return avgPagesString;
    }

    public void avgPagesPerDayFirebase(int avgPages, Firebase booksFirebaseRef) {
        Map<String, Object> bookMap = new HashMap<String, Object>();
        bookMap.put("avgPagesPerDay", avgPages);
        booksFirebaseRef.updateChildren(bookMap);
    }

}



