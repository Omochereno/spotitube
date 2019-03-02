package datasource;

import domain.Playlist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IPlaylistDAO {

    public ArrayList<Playlist> list();

    public ArrayList<Playlist> findByOwner(String owner);

    public ArrayList<Playlist> findByName(String name);

    public void save(Playlist playlist);

    public void update(Playlist playlist);

    public void delete(int playListId);
}
