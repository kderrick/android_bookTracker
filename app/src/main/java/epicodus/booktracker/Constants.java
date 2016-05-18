package epicodus.booktracker;

import android.os.Build;

/**
 * Created by ali on 5/16/16.
 */
public class Constants {
    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String GOOGLE_API_KEY = BuildConfig.GOOGLE_BOOK_API_KEY;

    public static final String FIREBASE_LOCATION_BOOKS = "books";
    public static final String FIREBASE_URL_BOOKS = FIREBASE_URL + "/" + FIREBASE_LOCATION_BOOKS;

    //newly added 2pm Wed May 18
    public static final String KEY_BOOKID = "BOOKID";

    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_UID = "UID";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    public static final String GOOGLE_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    public static final String GOOGLE_QUERY_PARAMETER = "q";
    public static final String PREFERENCES_SEARCHPARAM_KEY = "searchParam";

    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";


    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_BOOKS = "books";
}
