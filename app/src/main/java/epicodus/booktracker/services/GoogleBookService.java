package epicodus.booktracker.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import epicodus.booktracker.Constants;
import epicodus.booktracker.model.Book;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ali on 5/16/16.
 */
public class GoogleBookService {

    public static void findBook(String searchParam, Callback callback) {
        String GOOGLE_API_KEY = Constants.GOOGLE_API_KEY;

        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GOOGLE_API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.GOOGLE_QUERY_PARAMETER, searchParam);
        urlBuilder.addQueryParameter("key", GOOGLE_API_KEY);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Book> processResults(Response response) {
        ArrayList<Book> books = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject bookJSON = new JSONObject(jsonData);
                JSONArray resultsJSON = bookJSON.getJSONArray("items");
                for (int i = 0; i < resultsJSON.length(); i++) {
                    JSONObject basicInfoJSON = resultsJSON.getJSONObject(i);
                    JSONObject volumeInfoJSON = basicInfoJSON.getJSONObject("volumeInfo");
                    JSONObject imagesInfoJSON = volumeInfoJSON.optJSONObject("imageLinks");
                    JSONArray authorsInfoJSON = volumeInfoJSON.optJSONArray("authors");
                    JSONArray categoriesJSON = volumeInfoJSON.optJSONArray("categories");

                    String title = volumeInfoJSON.optString("title");
                    String author = "N/A";
                    if (authorsInfoJSON != null) {
                        author = authorsInfoJSON.getString(0);
                    }

                    String image = "";
                    if (imagesInfoJSON != null) {
                        image = imagesInfoJSON.getString("thumbnail");
                    }

                    String category = "N/A";
                    if (categoriesJSON != null) {
                        category = categoriesJSON.getString(0);
                        Log.v("TAG", category);
                    }

                    String description = "No description available.";
                    if (volumeInfoJSON != null ) {
                        description = volumeInfoJSON.optString("description");
                    }
                    double aveRating = basicInfoJSON.optDouble("averageRating");
                    String previewLink = volumeInfoJSON.optString("previewLink");
                    int pageCount = volumeInfoJSON.optInt("pageCount");
                    String publishedDate = volumeInfoJSON.optString("publishedDate");

                    Book book = new Book(title, author, image, description, aveRating, previewLink, pageCount, publishedDate, category);
                    books.add(book);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}