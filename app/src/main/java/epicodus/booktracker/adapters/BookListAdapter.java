package epicodus.booktracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.ui.BookDetailActivity;

/**
 * Created by ali on 5/16/16.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookViewHolder>  {
    private ArrayList<Book> mBooks = new ArrayList<>();
    private Context mContext;

    public BookListAdapter(Context context, ArrayList<Book> restaurants) {
        mContext = context;
        mBooks = restaurants;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        BookViewHolder viewHolder = new BookViewHolder(view, mBooks);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bindBook(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

}
