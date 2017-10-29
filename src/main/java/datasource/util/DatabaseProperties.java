package datasource.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    private static final String PROPERTIES_FILE = "database.properties";

    private String driver;
    private String connection_string;
    private String username;
    private String password;

    private Properties prop;

    public DatabaseProperties(){
        loadPropertyFile();
        setPropValues();

    }

    private void setPropValues() {
        driver = prop.getProperty("spotitube.jdbc.driver");
        connection_string = prop.getProperty("spotitube.jdbc.connection_string");
        username = prop.getProperty("spotitube.jdbc.username");
        password = prop.getProperty("spotitube.jdbc.password");
    }

    private void loadPropertyFile() {
        prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);

        try{
            if (inputStream != null)
                prop.load(inputStream);
            else
                throw new FileNotFoundException("property file '" + PROPERTIES_FILE + "' not found");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDriver() {
        return driver;
    }

    public String getConnection_string() {
        return connection_string;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
