package domain;

public class Availability {

    private boolean offlineAvailable;
    private String playlistName;
    private Track track;

    public Availability(String playlistName, Track track, boolean offlineAvailable){
        this.playlistName = playlistName;
        this.track = track;
        this.offlineAvailable = offlineAvailable;
    }


    public void toggle(){
        if(offlineAvailable == true)
            offlineAvailable = false;
        else
            offlineAvailable = true;
    }

    public boolean isOfflineAvailable(){
        return offlineAvailable;
    }
}
