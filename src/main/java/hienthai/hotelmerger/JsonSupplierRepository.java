package hienthai.hotelmerger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonSupplierRepository implements SupplierRepository {
    private static final String FILE_PATH = "data/supplier.json";

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> jsonData = objectMapper.readValue(new File(FILE_PATH), List.class);
            for (Map<String, Object> item : jsonData) {
                Supplier supplier = parse(item);
                suppliers.add(supplier);
            }

        } catch (IOException e) {
            System.err.println("Error reading json data: " + e.getMessage());
        }

        return suppliers;
    }

    private Supplier parse(Map<String, Object> item) {
        String endPoint = (String)item.get("end_point");
        HashMap<String, String> k2kMap = new HashMap<>();

        k2kMap.put("id", (String)item.get("id"));
        k2kMap.put("destination_id", (String)item.get("destination_id"));
        k2kMap.put("name", (String)item.get("name"));
        k2kMap.put("description", (String)item.get("description"));
        k2kMap.put("booking_conditions", (String)item.get("booking_conditions"));

        Map<String, Object> location = (Map<String, Object>)item.get("location");
        k2kMap.put("location.lat", (String)location.get("lat"));
        k2kMap.put("location.lng", (String)location.get("lng"));
        k2kMap.put("location.address", (String)location.get("address"));
        k2kMap.put("location.city", (String)location.get("city"));
        k2kMap.put("location.country", (String)location.get("country"));

        Map<String, Object> amenity = (Map<String, Object>)item.get("amenity");
        k2kMap.put("amenity.general", (String)amenity.get("general"));
        k2kMap.put("amenity.room", (String)amenity.get("room"));

        Map<String, Object> images = (Map<String, Object>)item.get("images");
        k2kMap.put("images.room", (String)images.get("room"));
        k2kMap.put("images.site", (String)images.get("site"));
        k2kMap.put("images.amenity", (String)images.get("amenity"));

        Map<String, Object> image = (Map<String, Object>)item.get("image");
        k2kMap.put("image.link", (String)image.get("link"));
        k2kMap.put("image.description", (String)image.get("description"));

        return new Supplier(endPoint, k2kMap);
    }
}
