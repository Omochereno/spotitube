package datasource.util;

import java.util.UUID;

public class UtilManager {

    public static String generateToken(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes()).toString();
    }
}
