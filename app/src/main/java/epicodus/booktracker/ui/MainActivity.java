package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.findBookButton) Button mFindBookButton;
    @Bind(R.id.bookEditText) EditText mFindBookEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String searchParam = mFindBookEditText.getText().toString();
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        intent.putExtra("searchParam", searchParam);
        startActivity(intent);
    }
}
