package cs160.final_proj_drawer;


import java.util.List;

public class ItineraryObject {
     private String creatorName;
     private String itineraryName;
     private int numLikes;
     private String coverPhoto;
     private int numStops;
     private List<Stop> stops;
     private List<String> tags;
     private List<String> access;

    public void ItineraryObject(String creatorName, String itineraryName, int numLikes,
                                String coverPhoto, int numStops, List<Stop> Stops,List<String> tags,
                                List<String>access) {
        // Create an itinerary object
        this.creatorName = creatorName;
        this.itineraryName = itineraryName;
        this.numLikes = numLikes;
        this.coverPhoto = coverPhoto;
        this.numStops = numStops;
        this.stops = stops;
        this.tags = tags;
        this.access = access;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public String getItineraryName() {
        return this.itineraryName;
    }

    public int getNumLikes() {
        return this.numLikes;
    }

    public int getNumStops() {
        return this.numStops;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<String> getAccess() {
        return this.access;
    }

}


