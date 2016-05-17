package epicodus.booktracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import epicodus.booktracker.model.Book;
import epicodus.booktracker.ui.BookDetailFragment;


/**
 * Created by Guest on 5/16/16.
 */
public class BookPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Book> mBooks;
    private String mSource;

    public BookPagerAdapter(FragmentManager fm, ArrayList<Book> books, String source) {
        super(fm);
        this.mBooks = books;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return BookDetailFragment.newInstance(mBooks, position, mSource);
    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBooks.get(position).getTitle();
    }

}
