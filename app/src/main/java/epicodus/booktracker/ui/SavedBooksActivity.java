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
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private Query mQuery;
    public ArrayList<Book> mBooks = new ArrayList<>();
    private Firebase mSearchedBookRef;
    private ValueEventListener mSearchedBookRefListener;
    private FirebaseBookListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_books);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SavedBooksActivity.this);
//         FIREBASE STUFF
//         mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
//         mSearchedBookRef = new Firebase(Constants.FIREBASE_URL + "/users/" + mSharedPreferences.getString("UID", "WRONG") + "/" + Constants.FIREBASE_BOOKS);
        setUpFirebaseQuery();
        setUpRecyclerView();

        mSearchedBookRefListener = mSearchedBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    String books = dataSnapshot.getValue().toString();
                    Log.d("Books updated", books);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSearchedBookRef.removeEventListener(mSearchedBookRefListener);
    }

    private void setUpFirebaseQuery() {
        String book = mSearchedBookRef.toString();
        mQuery = new Firebase(book).orderByChild("title");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseBookListAdapter(mQuery, Book.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

}
