package service;

import datasource.ILoginDAO;
import datasource.IPlaylistDAO;
import datasource.ITrackDAO;
import domain.JSON.Playlists;
import domain.JSON.UserToken;
import domain.Playlist;
import domain.Track;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService implements IPlaylistService {

    @Inject
    private ITrackDAO trackDAO;

    @Inject
    private IPlaylistDAO playlistDAO;

    @Inject
    private ILoginDAO loginDAO;

    @Override
    public Playlists getPlaylists(String token) {
        Playlists playlists = new Playlists();
        playlists.setPlaylists(playlistDAO.getPlaylists(token));

        int playlistLength = 0;
        for (Playlist playlist : playlists.getPlaylists()) {
            List<Track> tracks = trackDAO.getTracks(playlist.getId(), true);
            for (Track track : tracks) {
                playlistLength += track.getDuration();
            }
        }
        playlists.setLength(playlistLength);
        return playlists;
    }

    @Override
    public boolean deletePlaylist(int id) {
        return playlistDAO.deletePlaylist(id);
    }

    @Override
    public boolean createPlaylist(String token, Playlist playlist) {
        List<UserToken> users = loginDAO.getAllUsers();
        for (UserToken userToken : users) {
            if (token.equals(userToken.getToken())) {
                return playlistDAO.createPlaylist(userToken.getUser(), playlist);
            }
        }
        return false;
    }

    @Override
    public boolean editPlaylist(Playlist playlist) {
        return playlistDAO.editPlaylist(playlist);
    }
}
