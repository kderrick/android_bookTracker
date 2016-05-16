package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;

public class BookListActivity extends AppCompatActivity {
    @Bind(R.id.bookTextView) TextView mBookTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String book = intent.getStringExtra("book");
        mBookTextView.setText(book);

    }
}
