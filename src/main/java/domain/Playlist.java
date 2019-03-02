package domain;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private int id;
    private Owner owner;
    private String name;
    private List<Availability> tracks = new ArrayList<>();

    public Playlist(Owner owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Playlist(int id, String Owner, String name, List<Availability> tracks) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.tracks = tracks;
    }

    public void addTrack(Track track, boolean isOfflineAvailable){
        tracks.add(new Availability(this.getName(), track, isOfflineAvailable));
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Owner getOwner() {
        return owner;
    }

    public List<Availability> getTracks() {
        return tracks;
    }

    public void setTracks(List<Availability> tracks) {
        this.tracks = tracks;
    }

}
