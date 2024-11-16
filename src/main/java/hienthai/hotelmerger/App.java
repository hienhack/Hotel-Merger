package hienthai.hotelmerger;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    private final SupplierRepository supplierRepository = new JsonSupplierRepository();
    private final MergeStrategy mergeStrategy = new SimpleMergerStrategy();
    private Map<String, Hotel> hotels = new HashMap<>();

    public App() {
        prepareData();
    }

    private void prepareData() {
        List<Supplier> suppliers = supplierRepository.getAll();
        List<Map<String, Hotel>> data = new ArrayList<>();
        Set<String> hotelsId = new HashSet<>();

        for (Supplier supplier : suppliers) {
            Map<String, Hotel> hotels = supplier.fetch();
            data.add(hotels);
            hotelsId.addAll(hotels.keySet());
        }

        // Merge hotels with the same id
        for (String id : hotelsId) {
            List<Hotel> conflictHotels = new ArrayList<>();
            for (Map<String, Hotel> datum : data) {
                if (datum.containsKey(id)) {
                    conflictHotels.add(datum.get(id));
                }
            }
            Hotel hotel = mergeStrategy.merge(conflictHotels.toArray(new Hotel[1]));
        }
    }

    public List<Hotel> find(String[] ids, String[] desIds) {
        List<Hotel> result = new ArrayList<>();

        // Filter with hotel id
        for (String id : ids) {
            if (hotels.containsKey(id)) {
                result.add(hotels.get(id));
            }
        }

        // Filter with destination id
        if (desIds.length != 0) {
            Set<String> desIdSet = Set.of(desIds);
            result = result.stream().filter(hotel -> desIdSet.contains(hotel.getDestinationId()))
                    .collect(Collectors.toList());
        }

        return result;
    }

    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Invalid args elements count");
//            return;
//        }
//
//        String[] hotelIds = args[0].split(",");
//        String[] locationIds = args[1].split(",");
//
//        App app = new App();
//        Object json = app.find(hotelIds, locationIds);
//        System.out.println(json);

        SupplierRepository supplierRepository = new JsonSupplierRepository();
        List<Supplier> suppliers = supplierRepository.getAll();
        System.out.println(suppliers.size());
    }
}
