package epicodus.booktracker.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.Collections;

import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.util.FirebaseRecyclerAdapter;

/**
 * Created by chalmie on 5/16/16.
 */
public class FirebaseBookListAdapter extends FirebaseRecyclerAdapter<BookViewHolder, Book> {

    private Context mContext;

    public FirebaseBookListAdapter(Query query, Class<Book> itemClass) {
        super(query, itemClass);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.book_list_item_drag, parent, false);
        return new BookViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        holder.bindBook(getItem(position));
    }

    @Override
    protected void itemAdded(Book item, String key, int position) {

    }

    @Override
    protected void itemChanged(Book oldItem, Book newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Book item, String key, int position) {

    }

    @Override
    protected void itemMoved(Book item, String key, int oldPosition, int newPosition) {

    }
}
