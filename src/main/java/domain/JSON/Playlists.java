package domain.JSON;

import domain.Playlist;

import java.util.List;

public class Playlists {

    private List<Playlist> playlists;
    private int length;

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
