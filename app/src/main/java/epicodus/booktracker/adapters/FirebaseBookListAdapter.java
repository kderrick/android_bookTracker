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

import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.util.FirebaseRecyclerAdapter;
import epicodus.booktracker.util.ItemTouchHelperAdapter;
import epicodus.booktracker.util.OnStartDragListener;

/**
 * Created by chalmie on 5/16/16.
 */
public class FirebaseBookListAdapter extends FirebaseRecyclerAdapter<BookViewHolder, Book> implements ItemTouchHelperAdapter {

    private Context mContext;
    private final OnStartDragListener mDragStartListener;

    public FirebaseBookListAdapter(Query query, Class<Book> itemClass, OnStartDragListener dragStartListener) {
        super(query, itemClass);
        mDragStartListener = dragStartListener;
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
        holder.mAuthorTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String uid = sharedPreferences.getString(Constants.KEY_UID, null);
        Firebase ref = new Firebase(Constants.FIREBASE_URL_BOOKS).child(uid);
        String bookKey = getItem(position).getPushId();
        ref.child(bookKey).removeValue();
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
