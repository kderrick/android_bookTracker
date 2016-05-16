package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;

public class BookListActivity extends AppCompatActivity {
    @Bind(R.id.bookTextView) TextView mBookTextView;
    @Bind(R.id.listView) ListView mListView;

    private String[] books = new String [] {"Hunchback of Notre", "Catch in the Rye", "Harry Potter 1", "Horton Hears a Who"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String book = intent.getStringExtra("book");
        mBookTextView.setText(book);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);
        mListView.setAdapter(adapter);

    }
}
