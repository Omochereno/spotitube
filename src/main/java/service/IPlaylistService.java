package service;

import domain.JSON.Playlists;
import domain.Playlist;

public interface IPlaylistService {


     Playlists getPlaylists(String token);

     boolean deletePlaylist(int id);

    boolean createPlaylist(String token, Playlist playlist);

    boolean editPlaylist(Playlist playlist);
}
