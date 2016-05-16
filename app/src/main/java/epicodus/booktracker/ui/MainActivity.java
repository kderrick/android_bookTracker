package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.findBookButton) Button mFindBookButton;
    @Bind(R.id.bookEditText) EditText mFindBookEditText;
    //@Bind(R.id.saveBookButton) Button mSaveBookButton;

    private ValueEventListener mUserRefListener;
    private Firebase mUserRef;
    private String mUId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //mUId = mSharedPreferences.getString(Constants.KEY_UID, null);
        //mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mUId);

        mFindBookButton.setOnClickListener(this);
        //mSaveBookButton.setOnClickListener(this);

        //TODO: get rid of this code | not needed
        String searchParam = mFindBookEditText.getText().toString();
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        intent.putExtra("searchParam", searchParam);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.findBookButton:
                Intent newBookIntent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(newBookIntent);
                break;
           //case R.id.saveBookButton:
                //Intent savedBooksIntent = new Intent(MainActivity.this, SavedBooksActivity.class);
                //startActivity(savedBooksIntent);
                //break;
            default:
                break;
        }
    }

    //TODO: add menu inflation, user logout
}
