package epicodus.booktracker.ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.adapters.BookPagerAdapter;
import epicodus.booktracker.model.Book;

public class BookDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private BookPagerAdapter adapterViewPager;
    ArrayList<Book> mBooks = new ArrayList<>();
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mSource = intent.getStringExtra(Constants.KEY_SOURCE);
        mBooks = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_KEY_JOBS));
        Integer startingPosition = intent.getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        adapterViewPager = new BookPagerAdapter(getSupportFragmentManager(), mBooks, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
