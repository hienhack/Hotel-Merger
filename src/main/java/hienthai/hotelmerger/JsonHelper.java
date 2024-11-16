package hienthai.hotelmerger;

import java.util.Map;

public class JsonHelper {
    public static Object getFromKeyPath(String path, Map<String, Object> item) {
        String[] keys = path.split("\\.");

        Map<String, Object> currentObject = item;
        for (int i = 0; i < keys.length - 1; i++) {
            currentObject = (Map<String, Object>) currentObject.get(keys[i]);
        }

        return currentObject.get(keys[keys.length - 1]);
    }
}
