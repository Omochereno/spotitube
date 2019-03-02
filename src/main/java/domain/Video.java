package domain;

import java.util.Date;

public class Video extends Track {

    private int playCount;
    private Date publicationDate;
    private String description;

    public Video(String performer, String title, String url, int duration, int playCount, Date publicationDate, String description) {
        super(performer, title, url, duration);
        this.playCount = playCount;
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public int getPlayCount() {
        return playCount;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getPerformer() {
        return super.getPerformer();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public long getDuration() {
        return super.getDuration();
    }
}
