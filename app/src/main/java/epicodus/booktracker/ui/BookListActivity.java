package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.adapters.BookListAdapter;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.services.GoogleBookService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookListActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    public ArrayList<Book> mBooks = new ArrayList<>();
    private BookListAdapter mAdapter;
    //private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        //mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

        Intent intent = getIntent();

        //TODO:Intent is searchParam
        String searchParam = intent.getStringExtra("searchParam");
        getBook(searchParam);
    }

    private void getBook(String searchParam) {
        final GoogleBookService googleBookService = new GoogleBookService();

        googleBookService.findBook(searchParam, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mBooks = googleBookService.processResults(response);
                BookListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new BookListAdapter(getApplicationContext(), mBooks);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
