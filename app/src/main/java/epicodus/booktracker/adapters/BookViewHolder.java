package epicodus.booktracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.ui.SavedBooksActivity;

/**
 * Created by chalmie on 5/16/16.
 */
public class BookViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.titleTextView)
    TextView mTitleTextView;
    @Bind(R.id.authorTextView) TextView mAuthorTextView;


    private Context mContext;
    private ArrayList<Book> mBooks = new ArrayList<>();

    public BookViewHolder(View itemView, ArrayList<Book> books) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mBooks = books;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, SavedBooksActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("books", Parcels.wrap(mBooks));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindBook(Book book) {

        mTitleTextView.setText(book.getTitle());
        mAuthorTextView.setText(book.getAuthor());
    }

}
