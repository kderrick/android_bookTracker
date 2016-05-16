package epicodus.booktracker.services;

/**
 * Created by ali on 5/16/16.
 */
public class GoogleBookService {

//    public static void findBooks(String title, Callback callback) {
//        String API_KEY = Constants.API_KEY;
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BOOK_BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.BOOK_QUERY_TITLE_PARAMETER, title);
//        urlBuilder.addQueryParameter("key", API_KEY);
//
//        String url = urlBuilder.build().toString();
//        Log.v("URL", url);
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//    }
//
//    public ArrayList<Book> processResults(Response response) {
//        ArrayList<Book> books = new ArrayList<>();
//
//        try {
//            String jsonData = response.body().string();
//            if (response.isSuccessful()) {
//                JSONObject bookJSON = new JSONObject(jsonData);
//                JSONArray resultsJSON = bookJSON.getJSONArray("items");
//                for (int i = 0; i < resultsJSON.length(); i++) {
//                    JSONObject basicInfoJSON = resultsJSON.getJSONObject(i);
//                    JSONObject volumeInfoJSON = basicInfoJSON.getJSONObject("volumeInfo");
//                    JSONObject imagesInfoJSON = volumeInfoJSON.getJSONObject("imageLinks");
//                    JSONArray authorsInfoJSON = volumeInfoJSON.getJSONArray("authors");
//
//
//                    String title = volumeInfoJSON.getString("title");
//                    String publishedDate = volumeInfoJSON.getString("publishedDate");
//                    String pageCount = Integer.toString(volumeInfoJSON.getInt("pageCount"));
//                    String author = authorsInfoJSON.getString(0);
//                    String image = imagesInfoJSON.getString("thumbnail");
//                    String id = basicInfoJSON.getString("id");
//
//
//                    Book book = new Book(title,author,image,pageCount,publishedDate,id);
//                    books.add(book);
//                }
//            }
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        return books;
//    }
    }
