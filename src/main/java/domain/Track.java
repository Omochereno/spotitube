package domain;

import java.util.List;

public abstract class Track {

    //Variables
    private String performer;
    private String title;
    private String url;
    private int duration;

    public Track(String performer, String title, String url, int duration) {
        this.performer = performer;
        this.title = title;
        this.url = url;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "The track " + title +
        " is performed by: " + performer;
    }

    public String getPerformer() {
        return performer;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public long getDuration() {
        return duration;
    }


}
