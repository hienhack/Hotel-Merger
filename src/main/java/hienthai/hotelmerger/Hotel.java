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
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"id\": \"").append(id).append("\",\n");
        builder.append("  \"destination_id\": \"").append(destinationId).append("\",\n");
        builder.append("  \"name\": \"").append(name).append("\",\n");
        builder.append("  \"location\": {\n");
        builder.append("    \"lat\": ").append(locationLat).append(",\n");
        builder.append("    \"lng\": ").append(locationLng).append(",\n");
        builder.append("    \"address\": ").append(locationAddress).append(",\n");
        builder.append("    \"city\": ").append(locationCity).append(",\n");
        builder.append("    \"country\": ").append(locationCountry).append("\n");
        builder.append("  },\n");
        builder.append("  \"description\": \"").append(description).append("\",\n");

        builder.append("  \"amenities\": {\n");
        builder.append("    \"general\": [\"").append(String.join("\", \"", generalAmenities)).append("\"],");
        builder.append("    \"room\": [\"").append(String.join("\", \"", roomAmenities)).append("\"],");
        builder.append("  },\n");

        builder.append("  \"images\": {\n");
        builder.append("    \"rooms\": [\n");
        builder.append("      ").append(String.join(",\n", roomImages.stream().map(Image::toJson).toList()));
        builder.append("    ],\n");
        builder.append("    \"site\": [\n");
        builder.append("      ").append(String.join(",\n", siteImages.stream().map(Image::toJson).toList()));
        builder.append("    ],\n");
        builder.append("    \"amenities\": [\n");
        builder.append("      ").append(String.join(",\n", amenityImages.stream().map(Image::toJson).toList()));
        builder.append("    ],\n");

        builder.append("  \"booking_conditions\": [\n");
        builder.append("    \"").append(String.join("\",\n", bookingConditions)).append("\"\n");
        builder.append("  ]");
        builder.append("}");

        return builder.toString();
    }

    public String getId() {
        return this.id;
    }

    public String getDestinationId() {
        return this.destinationId;
    }
    public String getName() {
        return name;
    }

    public float getLocationLat() {
        return locationLat;
    }

    public float getLocationLng() {
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
