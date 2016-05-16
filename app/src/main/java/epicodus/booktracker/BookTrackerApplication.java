package epicodus.booktracker;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by ali on 5/16/16.
 */
public class BookTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
