package hienthai.hotelmerger;

import com.fasterxml.jackson.databind.ObjectMapper;
import hienthai.hotelmerger.model.Image;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Supplier {
    public static Set<String> keySet = Set.of(
            "id", "destination_id", "name", "location.lat", "location.lng", "location.address",
            "location.city", "location.country", "description", "amenity.general", "amenity.room",
            "images.room", "images.site", "images.amenity", "image.link", "image.description", "booking_conditions"
    );

    private String endPoint;
    private Map<String, String> key2KeyMap;

    public Supplier(String endPoint, Map<String, String> key2KeyMap) {
        this.key2KeyMap = key2KeyMap;
        this.endPoint = endPoint;

//        if (!key2KeyMap.keySet().containsAll(keySet)) {
//            throw new RuntimeException("Error mapping key from database");
//        }

        for (String key : keySet) {
            if (!key2KeyMap.containsKey(key)) {
                System.out.println("No mapping for key: " + key);
            }
        }
    }

    // Fetch data from the endpoint
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
            System.err.format("Error reading json data");
        } catch (Exception e) {
            System.err.format("Error fetching data from %s", endPoint);
        }

        return null;
    }

    private Hotel parse(Map<String, Object> item) {
        String id = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("id"), item);
        String name = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("name"), item);
        String destinationId = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("name"), item);
        float locationLat = (float)JsonHelper.getFromKeyPath(key2KeyMap.get("location.lat"), item);
        float locationLng = (float)JsonHelper.getFromKeyPath(key2KeyMap.get("location.lng"), item);
        String locationAddress = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("location.address"), item);
        String locationCity = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("location.city"), item);
        String locationCountry = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("location.country"), item);
        String description = (String)JsonHelper.getFromKeyPath(key2KeyMap.get("description"), item);
        List<String> generalAmenities = (List<String>)JsonHelper.getFromKeyPath(key2KeyMap.get("amenity.general"), item);
        List<String> roomAmenities = (List<String>)JsonHelper.getFromKeyPath(key2KeyMap.get("amenity.room"), item);

        List<Image> roomImages = (List<Image>)JsonHelper.getFromKeyPath(key2KeyMap.get(""), item);

        List<Image> siteImages = (List<Image>)JsonHelper.getFromKeyPath(key2KeyMap.get("name"), item);

        List<Image> amenityImages = (List<Image>)JsonHelper.getFromKeyPath(key2KeyMap.get("amenity"), item);

        List<String> bookingConditions = (List<String>)JsonHelper.getFromKeyPath(key2KeyMap.get("booking_conditions"), item);

        Hotel hotel = new Hotel(
                id, name, destinationId, locationLat, locationLng, locationAddress, locationCity, locationCountry,
                description, generalAmenities, roomAmenities, roomImages, siteImages, amenityImages, bookingConditions
        );

        return hotel;
    }
}
