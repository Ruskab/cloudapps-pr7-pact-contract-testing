package ikab.dev.planner.models;

import java.io.Serializable;

public class LandscapeResponse implements Serializable {
    
    private static final long serialVersionUID = 7053895734591440117L;

    private String id;
    private String landscape;

    public LandscapeResponse() {
    }

    public LandscapeResponse(String id, String landscape) {
        this.id = id;
        this.landscape = landscape;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    @Override
    public String toString() {
        return "LandscapeResponse{" +
                "id='" + id + '\'' +
                ", landscape='" + landscape + '\'' +
                '}';
    }
}