package hienthai.hotelmerger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class App {
    private final SupplierRepository supplierRepository = new JsonSupplierRepository();
    private final MergeStrategy mergeStrategy = new SimpleMergeStrategy();
    private final CleaningStrategy cleaningStrategy = new SimpleCleaningStrategy();
    private Map<String, Hotel> hotels = new HashMap<>();

    public App() {
        prepareData();
    }

    private void prepareData() {
        List<Supplier> suppliers = supplierRepository.getAll();
        List<Map<String, Hotel>> supplierFetchedHotels = new ArrayList<>();
        Set<String> hotelsId = new HashSet<>();

        for (Supplier supplier : suppliers) {
            Map<String, Hotel> hotels = supplier.fetch();

            // Clean data
            hotels.replaceAll((id, hotel) -> {
                return cleaningStrategy.clean(hotel);
            });

            supplierFetchedHotels.add(hotels);

            // Save distinct hotels' id
            hotelsId.addAll(hotels.keySet());
        }

        // Find all hotels with the same id and merge them
        for (String id : hotelsId) {
            List<Hotel> conflictHotels = new ArrayList<>();
            for (Map<String, Hotel> hotels : supplierFetchedHotels) {
                if (hotels.containsKey(id)) {
                    conflictHotels.add(hotels.get(id));
                }
            }

            Hotel hotel = mergeStrategy.merge(conflictHotels.toArray(new Hotel[1]));
            hotels.put(hotel.getId(), hotel);
        }
    }

    public List<Hotel> find(Set<String> ids, Set<Integer> desIds) {
        List<Hotel> result = hotels.values().stream().toList();

        // Filter with hotel id
        if (!ids.isEmpty()) {
            result = hotels.values().stream().filter(hotel -> ids.contains(hotel.getId())).toList();
        }

        // Filter with destination id
        if (!desIds.isEmpty()) {
            result = result.stream().filter(hotel -> desIds.contains(hotel.getDestinationId())).toList();
        }

        return result;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid args elements count");
            return;
        }

        Set<String> hotelIds = args[0].equals("none") ? new HashSet<>() : Set.of(args[0].split(","));
        Set<Integer> desIds = new HashSet<>();
        if (!args[1].equals("none")) {
            for (String desId : args[1].split(",")) {
                try {
                    desIds.add(Integer.valueOf(desId));
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Invalid destination id format: %s is not an integer", desId));
                }
            }
        }

        App app = new App();

        List<Hotel> searchResult = app.find(hotelIds, desIds);
        List<Map<String, Object>> dataMaps = searchResult.stream().map(Hotel::toDataMap).toList();

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String json = gson.toJson(dataMaps);
        System.out.println(json);
    }
}
