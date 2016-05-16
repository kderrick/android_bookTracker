package epicodus.booktracker;

import android.app.Application;

import com.firebase.client.Firebase;

public class BookTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
