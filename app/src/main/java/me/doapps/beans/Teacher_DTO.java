package me.doapps.beans;

/**
 * Created by HP on 31/08/2014.
 */
public class Teacher_DTO {
    private String id;
    private String name;
    private String description;
    private String image;
    private int rating;

    public Teacher_DTO() {
    }

    public Teacher_DTO(String id, String name, String description, String image, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }
}
