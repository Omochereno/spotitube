package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import domain.User;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class LoginDAO {

    private ConnectionManager connectionManager;
    private FileLogger fileLogger;

    @Inject
    public LoginDAO(ConnectionManager connectionManager, FileLogger fileLogger) {
        this.connectionManager = connectionManager;
        this.fileLogger = fileLogger;
    }

    public boolean isvalidUser(User user){
        User validuser = null;
        String query = "SELECT * FROM user WHERE user = ? AND pass = ?";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getUser());
            preparedStatement.setString(2, user.getPassword());
            validuser = getUserFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            fileLogger.log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
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
}
