package hienthai.hotelmerger;

import java.util.List;
import java.util.Set;

public class SimpleCleaningStrategy implements CleaningStrategy {
    // Stuck words exceptions, such as terms or abbreviations
    private static final Set<String> STUCK_WORD_EXCEPTIONS = Set.of("TV", "WiFi");

    @Override
    public Hotel clean(Hotel hotel) {
        String name = standardize(hotel.getName());
        String locationAddress = standardize(hotel.getLocationAddress());
        String locationCity = standardize(hotel.getLocationCity());
        String locationCountry = standardize(hotel.getLocationCountry());
        String description = standardize(hotel.getDescription());

        List<String> roomAmenities = standardize(hotel.getRoomAmenities());
        List<String> generalAmenities = standardize(hotel.getGeneralAmenities());
        List<String> bookingConditions = standardize(hotel.getBookingConditions());

        return new Hotel(hotel.getId(), name, hotel.getDestinationId(), hotel.getLocationLat(), hotel.getLocationLng(),
                locationAddress, locationCity, locationCountry, description, generalAmenities, roomAmenities,
                hotel.getRoomImages(), hotel.getSiteImages(), hotel.getAmenityImages(), bookingConditions
        );
    }

    private String standardize(String str) {
        if (str == null) return null;

        // Remove unnecessary spaces
        str = str.trim().replaceAll("\\s+", " ");

        // Separate the words that are stuck together, for example: BusinessCenter.
        String[] words = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (STUCK_WORD_EXCEPTIONS.contains(words[i])) continue;
            words[i] = words[i].replaceAll("(?<!^|\\s|[A-Z-])([A-Z])", " $1");
        }
        str = String.join(" ", words);

        // Capitalize the first letter of a sentence
        String[] sentences = str.split("(?<=[.!?])\\s*");
        StringBuilder builder = new StringBuilder();
        for (String sentence : sentences) {
            if (!sentence.isBlank()) {
                builder.append(Character.toUpperCase(sentence.trim().charAt(0))) // Capitalize first letter
                        .append(sentence.trim().substring(1)) // Append the rest of the sentence
                        .append(" ");
            }
        }
        str = builder.toString().trim();

        return str;
    }

    private List<String> standardize(List<String> list) {
        if (list == null) return null;

        return list.stream().map(this::standardize).toList();
    }
}