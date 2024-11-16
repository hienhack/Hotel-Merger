package hienthai.hotelmerger;

import hienthai.hotelmerger.model.Image;

import java.util.List;

public class Hotel {
    private String id;
    private String name;
    private String destinationId;
    private float locationLat;
    private float locationLng;
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
            String id, String name, String destinationId, float locationLat, float locationLng,
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

    public String toJson() {
        return null;
    }

    public String getId() { return this.id; }

    public String getDestinationId() {
        return this.destinationId;
    }

    // Other getters and setters are omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hotel)) return false;
        return ((Hotel) o).getId().equals(this.id);
    }
}
