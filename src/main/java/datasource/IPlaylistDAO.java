package datasource;

import domain.Playlist;

import java.lang.reflect.Array;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public interface IPlaylistDAO {

    List<Playlist> getPlaylists();

    List<Playlist> findByOwner(String owner);

    boolean deletePlaylist(int id);

    boolean createPlaylist(Playlist playlist);
}
