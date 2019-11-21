package cs160.final_proj_drawer;

import java.util.List;

public class Stop {
    private List<String> photos;
    private String name;
    private String location;
    private String description;
    private int index;


    public Stop(List<String> photos, String name, String location, String description, int index) {
        // Create a Stop instance
        this.photos = photos;
        this.name = name;
        this.location = location;
        this.description = description;
        this.index = index;

    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getPhotos() {
        return this.photos;
    }

    public int getIndex() {
        return this.index;
    }
}