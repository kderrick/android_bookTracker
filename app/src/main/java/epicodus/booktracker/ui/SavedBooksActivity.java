package epicodus.booktracker.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.adapters.FirebaseBookListAdapter;
import epicodus.booktracker.model.Book;

public class SavedBooksActivity extends AppCompatActivity {
    private Query mQuery;
    private Firebase mFirebaseRef;
    private FirebaseBookListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL_BOOKS);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String book = mFirebaseRef.child(userUid).toString();
        mQuery = new Firebase(book).orderByChild("title");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseBookListAdapter(mQuery, Book.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onPause() {
        String uid = mSharedPreferences.getString(Constants.KEY_UID, null);
        super.onPause();
        for (Book book : mAdapter.getItems()) {
            String pushID = book.getPushId();
            book.setIndex(Integer.toString(mAdapter.getItems().indexOf(book)));
            mFirebaseRef.child(uid)
                    .child(pushID)
                    .setValue(book);
        }
    }
}
