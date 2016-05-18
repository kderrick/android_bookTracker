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
import epicodus.booktracker.Constants;
import epicodus.booktracker.R;
import epicodus.booktracker.model.Book;
import epicodus.booktracker.ui.BookDetailActivity;
import epicodus.booktracker.ui.SavedBooksActivity;

/**
 * Created by chalmie on 5/16/16.
 */
public class BookViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.bookNameTextView) TextView mTitleTextView;
    @Bind(R.id.authorTextView) TextView mAuthorTextView;


    private ArrayList<Book> mBooks = new ArrayList<>();
    private Context mContext;
    private Integer mPosition;

    public BookViewHolder(View itemView, ArrayList<Book> books) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mBooks = books;

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int mPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                intent.putExtra(Constants.EXTRA_KEY_JOBS, Parcels.wrap(mBooks));

                if (mContext.getClass().getSimpleName().equals(SavedBooksActivity.class.getSimpleName())) {
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                } else {
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                }

                mContext.startActivity(intent);
            }
        });
    }

    public void bindBook(Book book) {
        mTitleTextView.setText(book.getTitle());
        mAuthorTextView.setText(book.getAuthor());
    }

}
