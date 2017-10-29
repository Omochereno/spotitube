package domain;

public class Song extends Track{

    private String album;

    public Song(String performer, String title, String url, long duration) {
        super(performer, title, url, duration);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


}
