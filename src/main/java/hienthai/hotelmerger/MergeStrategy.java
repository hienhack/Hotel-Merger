package hienthai.hotelmerger;


public interface MergeStrategy {
    // Merge hotels with the same id
    public Hotel merge(Hotel... hotels);
}
