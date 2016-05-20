package epicodus.booktracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.findBookButton) Button mFindBooksButton;
    @Bind(R.id.savedBooksButton) Button mSavedBooksButton;
    @Bind(R.id.welcomeTextView) TextView mWelcomeTextView;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.totalLibraryTextView) TextView mTotalLibraryTextView;
    @Bind(R.id.finishedBookTextView) TextView mFinishedBookTextView;

    private ValueEventListener mUserRefListener;
    private ValueEventListener mUserBookRefListener;
    private Firebase mUserRef;
    private String mUId;
    private SharedPreferences mSharedPreferences;
    private Firebase mFirebaseRef;
    private Firebase mFirebaseRefBooks;
//    private ArrayList<Book> bookArray = new ArrayList<>();
//    private ArrayList<Book> finishedBookArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Spannable spannable = (Spannable)mAppNameTextView.getText();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources()
                .getColor(R.color.colorOrange));
        spannable.setSpan( colorSpan, 4, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE );

        //grabs username to show on welcome screen
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUId = mSharedPreferences.getString(Constants.KEY_UID, null);
        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mUId);

        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);




        mFindBooksButton.setOnClickListener(this);
        mSavedBooksButton.setOnClickListener(this);

        final String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        mFirebaseRefBooks = new Firebase(Constants.FIREBASE_URL_BOOKS + "/" + userUid);
//      allBooks provides link to logged in users books

        mUserBookRefListener = mFirebaseRefBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Book> bookArray = new ArrayList<>();
                ArrayList<Book> finishedBookArray = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    bookArray.add(book);
                    if( book.getCurrentPage() >= book.getPageCount()) {
                        finishedBookArray.add(book);
                    }
                }
                mTotalLibraryTextView.setText(bookArray.size()+"");
                mFinishedBookTextView.setText(finishedBookArray.size()+"");
//
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", "Read failed");
            }
        });


        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getName() != null) {
                    mWelcomeTextView.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", "Read failed");
            }
        });

//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/androidnation.ttf");
//        mAppNameTextView.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void logout() {
        mFirebaseRef.unauth();
        takeUserToLoginScreenOnUnAuth();
    }
    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findBookButton:
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
                break;
            case R.id.savedBooksButton:
                Intent savedIntent = new Intent(MainActivity.this, SavedBooksActivity.class);
                startActivity(savedIntent);
                break;
            default:
                break;
        }
    }


}
