package datasource;

import domain.Playlist;

import java.util.List;

public interface IPlaylistDAO {

    public List list();

    public List findByOwner();

    public void save(Playlist playlist);

    public void delete(int playListId);
}
