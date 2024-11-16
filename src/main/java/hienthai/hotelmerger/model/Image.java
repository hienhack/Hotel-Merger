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

    public String toJson() {
        return String.format("{\"link\": \"%s\", \"description\": \"%s\"}", link, description);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Image)) return false;
        return ((Image) o).link.equals(this.link);
    }
}
