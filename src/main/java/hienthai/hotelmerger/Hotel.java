package hienthai.hotelmerger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Hotel {
    private String id;
    private String name;
    private Integer destinationId;
    private Double locationLat;
    private Double locationLng;
    private String locationAddress;
    private String locationCity;
    private String locationCountry;
    private String description;
    private List<String> generalAmenities;
    private List<String> roomAmenities;
    private List<Image> roomImages;
    private List<Image> siteImages;
    private List<Image> amenityImages;
    private List<String> bookingConditions;

    public Hotel(
            String id, String name, Integer destinationId, Double locationLat, Double locationLng,
            String locationAddress, String locationCity, String locationCountry, String description,
            List<String> generalAmenities, List<String> roomAmenities, List<Image> roomImages,
            List<Image> siteImages, List<Image> amenityImages, List<String> bookingConditions
    ) {
         this.id = id;
         this.name = name;
         this.destinationId = destinationId;
         this.locationLat = locationLat;
         this.locationLng = locationLng;
         this.locationAddress = locationAddress;
         this.locationCity = locationCity;
         this.locationCountry = locationCountry;
         this.description = description;
         this.generalAmenities = generalAmenities;
         this.roomAmenities = roomAmenities;
         this.roomImages = roomImages;
         this.siteImages = siteImages;
         this.amenityImages = amenityImages;
         this.bookingConditions = bookingConditions;
    }

    // Mapping data to a map which is used to convert to json
    public Map<String, Object> toDataMap() {
        Map<String, Object> dataMap = new LinkedHashMap<>();

        dataMap.put("id", id);
        dataMap.put("destination_id", destinationId);
        dataMap.put("name", name);

        Map<String, Object> location = new LinkedHashMap<>();
        location.put("lat", locationLat);
        location.put("lng", locationLng);
        location.put("address", locationAddress);
        location.put("city", locationCity);
        location.put("country", locationCountry);
        dataMap.put("location", location);

        dataMap.put("description", description);
        dataMap.put("amenities", Map.of(
           "general", generalAmenities,
           "room", roomAmenities
        ));

        dataMap.put("images", Map.of(
                "rooms", roomImages,
                "site", siteImages,
                "amenities", amenityImages
        ));

        dataMap.put("booking_conditions", bookingConditions);

        return dataMap;
    }

    public String getId() {
        return this.id;
    }

    public Integer getDestinationId() {
        return this.destinationId;
    }
    public String getName() {
        return name;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Double getLocationLng() {
        return locationLng;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGeneralAmenities() {
        return generalAmenities;
    }

    public List<String> getRoomAmenities() {
        return roomAmenities;
    }

    public List<Image> getRoomImages() {
        return roomImages;
    }

    public List<Image> getSiteImages() {
        return siteImages;
    }

    public List<Image> getAmenityImages() {
        return amenityImages;
    }

    public List<String> getBookingConditions() {
        return bookingConditions;
    }

    // Setters are omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hotel)) return false;
        return ((Hotel) o).getId().equals(this.id);
    }
}
