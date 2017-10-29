package datasource;

import java.util.List;

public interface ITrackDAO {

    public List list();

    public List findByTitle();
}
