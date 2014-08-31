package me.doapps.beans;

/**
 * Created by HP on 31/08/2014.
 */
public class Teacher_DTO {
    private String name;
    private String description;

    public Teacher_DTO(){}

    public Teacher_DTO(String name, String description){
        this.name = name;
        this.description = description;
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
}
