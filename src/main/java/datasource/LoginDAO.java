package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import datasource.util.UtilManager;
import domain.JSON.UserToken;
import domain.User;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class LoginDAO implements ILoginDAO {

    private ConnectionManager connectionManager;

    @Inject
    public LoginDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean isvalidUser(User user){
        User validuser = null;
        String query = "SELECT * FROM user WHERE user = ? AND pass = ?";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getUser());
            preparedStatement.setString(2, user.getPassword());
            validuser = getUserFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }
        return validuser == null ? false : true;
    }

    private User getUserFromResultSet(ResultSet res) throws SQLException   {
        User result = null;

        while(res.next()){
            result = new User();
            result.setUser(res.getString("user"));
            result.setPassword(res.getString("pass"));
        }
        res.close();
        return result;
    }

    @Override
    public List<UserToken> getAllUsers(){
        List<UserToken> userTokens = null;
        String query = "SELECT user FROM user";
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
           ResultSet res = preparedStatement.executeQuery(query);
           while(res.next()){
               UserToken token = new UserToken();
               token.setUser(res.getString("user"));
               token.setToken(UtilManager.generateToken(res.getString("user")));
               userTokens.add(token);
           }
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }
        return userTokens;
    }
}
