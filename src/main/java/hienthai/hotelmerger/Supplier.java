package hienthai.hotelmerger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Supplier {
    private String endPoint;
    private Map<String, String> key2KeyMap;

    public Supplier(String endPoint, Map<String, String> key2KeyMap) {
        this.key2KeyMap = key2KeyMap;
        this.endPoint = endPoint;
    }

    public Map<String, Hotel> fetch() {
        Map<String, Hotel> hotels = new HashMap<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.endPoint))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();

                List<Map<String, Object>> jsonData = objectMapper.readValue(response.body(), List.class);
                for (Map<String, Object> item : jsonData) {
                    Hotel hotel = parse(item);
                    hotels.put(hotel.getId(), hotel);
                }

        } catch (IOException e) {
            throw new RuntimeException("Error reading json data");
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error while fetching data from %s: %s\n", endPoint, e.getMessage()));
        }

        return hotels;
    }

    // Parse an object of mapped json to Hotel object
    private Hotel parse(Map<String, Object> item) {
        String id = (String) getFromKeyPath(key2KeyMap.get("id"), item);
        String name = (String) getFromKeyPath(key2KeyMap.get("name"), item);

        Integer destinationId = Integer.valueOf("" + getFromKeyPath(key2KeyMap.get("destination_id"), item));

        Double locationLat = convertToDouble("" + getFromKeyPath(key2KeyMap.get("location.lat"), item));
        Double locationLng = convertToDouble("" + getFromKeyPath(key2KeyMap.get("location.lng"), item));

        String locationAddress = (String) getFromKeyPath(key2KeyMap.get("location.address"), item);
        String locationCity = (String) getFromKeyPath(key2KeyMap.get("location.city"), item);
        String locationCountry = (String) getFromKeyPath(key2KeyMap.get("location.country"), item);

        String description = (String) getFromKeyPath(key2KeyMap.get("description"), item);

        List<String> generalAmenities = (List<String>) getFromKeyPath(key2KeyMap.get("amenity.general"), item);
        List<String> roomAmenities = (List<String>) getFromKeyPath(key2KeyMap.get("amenity.room"), item);

        List<Image> roomImages = convertToImageList((List<Map<String, String>>) getFromKeyPath(key2KeyMap.get("images.room"), item));
        List<Image> siteImages = convertToImageList((List<Map<String, String>>) getFromKeyPath(key2KeyMap.get("images.site"), item));
        List<Image> amenityImages = convertToImageList((List<Map<String, String>>) getFromKeyPath(key2KeyMap.get("images.amenity"), item));

        List<String> bookingConditions = (List<String>) getFromKeyPath(key2KeyMap.get("booking_conditions"), item);

        Hotel hotel = new Hotel(
                id, name, destinationId, locationLat, locationLng, locationAddress, locationCity, locationCountry,
                description, generalAmenities, roomAmenities, roomImages, siteImages, amenityImages, bookingConditions
        );

        return hotel;
    }

    private Double convertToDouble(String value) {
        Double result = null;
        try {
            result = Double.valueOf(value);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    private List<Image> convertToImageList(List<Map<String, String>> list) {
        if (list == null) return null;
        List<Image> result = new ArrayList<>();
        for (Map<String, String> imgMap : list) {
            result.add(new Image(imgMap.get(key2KeyMap.get("image.link")),
                    imgMap.get(key2KeyMap.get("image.description"))));
        }

        return result;
    }

    // Get the value of a field in mapped json object using its path
    private Object getFromKeyPath(String path, Map<String, Object> item) {
        if (path == null) return null;
        String[] keys = path.split("\\.");

        Map<String, Object> currentObject = item;
        for (int i = 0; i < keys.length - 1; i++) {
            currentObject = (Map<String, Object>) currentObject.get(keys[i]);
        }

        return currentObject.get(keys[keys.length - 1]);
    }
}
