package cs160.final_proj_drawer.logic;

import java.io.Serializable;

/*
    This class holds data used to make Searches
 */
public class SearchQueryObject implements Serializable {


    private String[] tags;
    private String location;


    public SearchQueryObject() {
        String[] tags = {""};
        this.tags = tags;
        this.location = "";
    }

    public SearchQueryObject(String[] tags, String location) {
        this.tags = tags;
        this.location = location;
    }

    public String[] getTags() {
        return this.tags;
    }

    public String getLocation() {
        return this.location;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
