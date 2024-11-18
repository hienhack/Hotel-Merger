package hienthai.hotelmerger;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleMergeStrategy implements MergeStrategy {
    @Override
    public Hotel merge(Hotel... hotels) {
        if (hotels.length == 0) {
            throw new RuntimeException("Invalid arguments count");
        } else if (hotels.length == 1) {
            return hotels[0];
        }


        String id = hotels[0].getId();
        Integer destinationId = hotels[0].getDestinationId();

        String name = mergeString(Arrays.stream(hotels).map(Hotel::getName).toList());

        Double lat = findMostFrequentElement(Arrays.stream(hotels).map(Hotel::getLocationLat).toList());
        Double lng = findMostFrequentElement(Arrays.stream(hotels).map(Hotel::getLocationLng).toList());
        String address = mergeString(Arrays.stream(hotels).map(Hotel::getLocationAddress).toList());
        String city = mergeString(Arrays.stream(hotels).map(Hotel::getLocationCity).toList());
        String country = mergeString(Arrays.stream(hotels).map(Hotel::getLocationCountry).toList());

        // Choose the longest description
        String description = Arrays.stream(hotels).map(Hotel::getDescription)
                .filter(Objects::nonNull)
                .reduce((d1, d2) -> d1.length() >= d2.length() ? d1 : d2).get();

        List<String> roomAmenities = mergeListString(Arrays.stream(hotels).map(Hotel::getRoomAmenities).toList());
        List<String> generalAmenities = mergeListString(Arrays.stream(hotels).map(Hotel::getGeneralAmenities).toList());

        // Remove duplicated amenities from general list
        Set<String> compareList = roomAmenities.stream().map(String::toUpperCase).collect(Collectors.toSet());
        generalAmenities = generalAmenities.stream().filter(str -> !compareList.contains(str.toUpperCase())).toList();

        List<Image> roomImages = mergeListImage(Arrays.stream(hotels).map(Hotel::getRoomImages).toList());
        List<Image> siteImages = mergeListImage(Arrays.stream(hotels).map(Hotel::getSiteImages).toList());
        List<Image> amenityImages = mergeListImage(Arrays.stream(hotels).map(Hotel::getAmenityImages).toList());

        List<String> bookingConditions = mergeListString(Arrays.stream(hotels).map(Hotel::getBookingConditions).toList());

        return new Hotel(id, name, destinationId, lat, lng, address, city, country, description,
                generalAmenities, roomAmenities, roomImages, siteImages, amenityImages, bookingConditions);
    }

    // Find the most frequent element in a list
    // If there are multiple value with the same frequency, return the first one
    private <T> T findMostFrequentElement(List<T> list) {
        Map<T, Integer> frequency = new HashMap<>();
        T maxObject = null;
        int maxCount = 0;

        for (T element : list) {
            if (element == null) continue;

            int count = frequency.getOrDefault(element, 0) + 1;
            frequency.put(element, count);
            if (maxCount < count) {
                maxObject = element;
            }
        }

        return maxObject;
    }

    // Merge a string field by choosing the most frequent value
    private String mergeString(List<String> list) {
        List<String> uppercaseList = list.stream().filter(Objects::nonNull).map(String::toUpperCase).toList();
        String temp = findMostFrequentElement(uppercaseList);
        int index = uppercaseList.indexOf(temp);
        String result = list.stream().filter(Objects::nonNull).toList().get(index);
        return result;
    }

    // Merge images to keep each link appears once
    // If there are multiple images with the same link,
    // merge all the descriptions together to create a new description
    private List<Image> mergeListImage(List<List<Image>> list) {
        Map<String, Set<String>> imagesMap = new HashMap<>();

        for (List<Image> images : list) {
            if (images == null) continue;
            for (Image img : images) {
                if (!imagesMap.containsKey(img.getLink())) {
                    Set<String> desSet = new HashSet<>();
                    desSet.add(img.getDescription());
                    imagesMap.put(img.getLink(), desSet);
                } else {
                    // Image link existed -> combine the descriptions
                    imagesMap.compute(img.getLink(), (link, desSet) -> {
                        desSet.add(img.getDescription());
                        return  desSet;
                    });
                }
            }
        }

        List<Image> result = new ArrayList<>();
        imagesMap.forEach((link, desSet) -> {
            result.add(new Image(link, desSet.stream().reduce((r, des) -> r + " - " + des).get()));
        });
        return result;
    }

    // Merge multiple lists of strings to a single list, guaranteeing no duplicated strings
    private List<String> mergeListString(List<List<String>> list) {
        Map<String, String> existed = new HashMap<>();

        for (List<String> strList : list) {
            if (strList == null) continue;
            for (String str : strList) {
                if (str == null || str.isBlank()) continue;
                if (!existed.containsKey(str.toUpperCase())) {
                    existed.put(str.toUpperCase(), str);
                }
            }
        }

        List<String> result = existed.values().stream().toList();
        return result;
    }
}
