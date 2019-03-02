package datasource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class DatabaseProperties {
    private static final String PROPERTIES_FILE = "database.properties";

    Properties property = new Properties();

    public DatabaseProperties() throws IOException, ClassNotFoundException {
        FileLogger logger = new FileLogger();
        try {
            property.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            logger.log(getClass().getName(), Level.SEVERE, "Unable to read database property");
        }
        Class.forName(property.getProperty("driverMYSQL"));
    }

    public String getDriver() {
        return property.getProperty("driverMYSQL");
    }

    public String getConnectionString() {
        return property.getProperty("connectionStringMYSQL");
    }

}
