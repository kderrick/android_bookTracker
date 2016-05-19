package epicodus.booktracker.model;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ali on 5/16/16.
 */
@Parcel
public class Book {
    String title;
    String author;
    String image;
    String description;
    double aveRating;
    String previewLink;
    int pageCount;
    String publishedDate;
    private String pushId;
    private String index;

    Date startDate;
    Date endDate;
    int currentPage;
    int avgPagesPerDay = 0;
    public Book() {}

    public Book(String title, String author, String image, String description, double aveRating, String previewLink, int pageCount, String publishedDate) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.description = description;
        this.aveRating = aveRating;
        this.previewLink = previewLink;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
        this.index = "not_specified";
        this.currentPage = 0;
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

    public double getAveRating() {
        return aveRating;
    }

    public String getPreviewLink() {return previewLink;}

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAvgPagesPerDay() {
        return avgPagesPerDay;
    }

    public void setAvgPagesPerDay(int avePagesPerDay) {
        this.avgPagesPerDay = avePagesPerDay;
    }
}