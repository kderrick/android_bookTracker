package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.findBookButton) Button mFindBookButton;
    @Bind(R.id.bookEditText) EditText mBookEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book = mBookEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });





    }
}
