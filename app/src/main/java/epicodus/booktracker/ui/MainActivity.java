package epicodus.booktracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.findBookButton) Button mFindBookButton;
    @Bind(R.id.bookEditText) EditText mFindBookEditText;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFindBookButton.setOnClickListener(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();




        String searchParam = mFindBookEditText.getText().toString();
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        intent.putExtra("searchParam", searchParam);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindBookButton) {
            String searchParam = mFindBookEditText.getText().toString();
            if (!(searchParam).equals("")) {
                addToSharedPreferences(searchParam);
            }
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            startActivity(intent);

        }
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_SEARCHPARAM_KEY, location).apply();
    }
}
