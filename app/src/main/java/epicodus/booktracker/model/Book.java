package epicodus.booktracker.model;

import org.parceler.Parcel;

/**
 * Created by ali on 5/16/16.
 */
@Parcel
public class Book {
    String title;
    String author;
    String image;
    String description;
    String mainCategory;
    double aveRating;
    double retailPrice;
    int pageCount;
    String publishedDate;
    String pushId;

    //for user to save progress of book variables - no getters or setters yet
    String startDate; //date var
    String endDate;  //date var
    int currentPage;
    //avePagesPerDay(); method based on pageCount, currentPage and startDate

    public Book() {}

    public Book(String title, String author, String image, String description, String mainCategory, double aveRating, double retailPrice, int pageCount, String publishedDate) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.description = description;
        this.mainCategory = mainCategory;
        this.aveRating = aveRating;
        this.retailPrice = retailPrice;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public double getAveRating() {
        return aveRating;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
