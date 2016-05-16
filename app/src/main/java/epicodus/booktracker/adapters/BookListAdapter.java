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
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    private ArrayList<Book> mBooks = new ArrayList<>();
    private Context mContext;

    public BookListAdapter(Context context, ArrayList<Book> books) {
        mContext = context;
        mBooks = books;
    }

    @Override
    public BookListAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: design book_list_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        BookViewHolder viewHolder = new BookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookListAdapter.BookViewHolder holder, int position) {
        holder.bindBook(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bookNameTextView) TextView mTitleTextView;
        @Bind(R.id.bookImageView) ImageView mBookImageView;
        @Bind(R.id.authorTextView) TextView mAuthorTextView;
        private Context mContext;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int itemPosition = getLayoutPosition();

                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra("position", itemPosition + "");
                    intent.putExtra("books", Parcels.wrap(mBooks));
                    mContext.startActivity(intent);
                }
            });
        }

        public void bindBook(Book book) {

            mTitleTextView.setText(book.getTitle());
            mAuthorTextView.setText(book.getAuthor());
            Picasso.with(mContext).load(book.getImage()).into(mBookImageView);
        }
    }
}