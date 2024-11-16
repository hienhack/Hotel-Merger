package hienthai.hotelmerger.model;

public class Image {
    private String link;
    private String description;

    public Image(String link, String description) {
        this.link = link;
        this.description = description;
    }

    public String getLink() { return this.link; }
    public String getDescription() { return this.description; }
}
