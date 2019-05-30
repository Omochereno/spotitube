package datasource;

import domain.Playlist;

import javax.ws.rs.QueryParam;
import java.lang.reflect.Array;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public interface IPlaylistDAO {

    boolean deletePlaylist(int id);

    boolean createPlaylist(String owner, Playlist playlist);

    List<Playlist> getPlaylists(String token);

    boolean editPlaylist(Playlist playlist);
}
