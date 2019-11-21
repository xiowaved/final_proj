package cs160.final_proj_drawer;

import java.util.List;

public class Stop {
    private List<String> photos;
    private String name;
    private String location;
    private int index;


    public void Stop(List<String> photos, String name, String location, int index) {
        // Create a Stop instance
        this.photos = photos;
        this.name = name;
        this.location = location;
        this.index = index;

    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }
    public List<String> getPhotos() {
        return this.photos;
    }

    public int getIndex() {
        return this.index;
    }
}