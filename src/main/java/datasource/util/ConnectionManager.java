package datasource.util;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class ConnectionManager implements IConnectionManager {

    private FileLogger logger;
    private static DatabaseProperties properties;
    private Connection connection = null;

    @Inject
    public ConnectionManager(DatabaseProperties databaseProperties) throws ClassNotFoundException {
        logger = new FileLogger();
        this.properties = databaseProperties;
        Class.forName(properties.getDriver());
        }

    @Override
    public Connection getConnection() throws SQLException {
        try
        {
            connection = DriverManager.getConnection(properties.getConnectionString());
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}
