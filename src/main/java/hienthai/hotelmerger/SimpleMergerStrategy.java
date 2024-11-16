package hienthai.hotelmerger;

import hienthai.hotelmerger.model.Image;

import java.util.*;
import java.util.function.Function;

public class SimpleMergerStrategy implements MergeStrategy {
    @Override
    public Hotel merge(Hotel... hotels) {
        int len = hotels.length;
        if (len < 2)
            throw  new RuntimeException("Invalid arguments count");

        String id = hotels[0].getId();
        String destinationId = hotels[0].getDestinationId();

        String name = mergeString(Arrays.stream(hotels).map(Hotel::getName).toList());

        float lat = findMostFrequentElement(Arrays.stream(hotels).map(Hotel::getLocationLat).toList());
        float lng = findMostFrequentElement(Arrays.stream(hotels).map(Hotel::getLocationLng).toList());
        String address = mergeString(Arrays.stream(hotels).map(Hotel::getLocationAddress).toList());
        String city = mergeString(Arrays.stream(hotels).map(Hotel::getLocationCity).toList());
        String country = mergeString(Arrays.stream(hotels).map(Hotel::getLocationCountry).toList());

        String description = Arrays.stream(hotels).map(Hotel::getDescription)
                .reduce((d1, d2) -> d1.length() >= d2.length() ? d1 : d2).get();

        List<String> generalAmenities = Arrays.stream(hotels).map(Hotel::getGeneralAmenities)
                .filter(conditions -> !conditions.isEmpty()).reduce((l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }).get();

        List<String> roomAmenities = Arrays.stream(hotels).map(Hotel::getRoomAmenities)
                .filter(conditions -> !conditions.isEmpty()).reduce((l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }).get();

        List<Image> roomImages = mergeImageList(Arrays.stream(hotels).map(Hotel::getRoomImages).toList());
        List<Image> siteImages = mergeImageList(Arrays.stream(hotels).map(Hotel::getSiteImages).toList());
        List<Image> amenityImages = mergeImageList(Arrays.stream(hotels).map(Hotel::getAmenityImages).toList());

        List<String> bookingConditions = Arrays.stream(hotels).map(Hotel::getBookingConditions)
                .filter(conditions -> !conditions.isEmpty()).reduce((l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }).get();


        return new Hotel(id, name, destinationId, lat, lng, address, city, country, description,
                generalAmenities, roomAmenities, roomImages, siteImages, amenityImages, bookingConditions);
    }

    private String mergeString(List<String> list) {
        return "";
    }

    // Most frequently merge
    private <T> T mergeField(List<T> list) {


        return null;
    }

    private <T> T findMostFrequentElement(List<T> list) {
        Map<T, Integer> frequency = new HashMap<>();
        T maxObject = null;
        int maxCount = 0;

        for (T element : list) {
            int count = frequency.getOrDefault(element, 0) + 1;
            frequency.put(element, count);
            if (maxCount < count) {
                maxObject = element;
            }
        }

        return maxObject;
    }

    // Merge images to keep each link appears once
    // If there are multiple images with the same link,
    // merge all the descriptions together to create a new description
    private List<Image> mergeImageList(List<List<Image>> list) {
        Map<String, Set<String>> imagesMap = new HashMap<>();

        for (List<Image> images : list) {
            for (Image img : images) {
                if (!imagesMap.containsKey(img.getLink())) {
                    imagesMap.put(img.getLink(), Set.of(img.getLink()));
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
}
