package datasource;

import domain.JSON.UserToken;
import domain.User;

import java.util.List;

public interface ILoginDAO {
    boolean isvalidUser(User user);

    List<UserToken> getAllUsers();
}
