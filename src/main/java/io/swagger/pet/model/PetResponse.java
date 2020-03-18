package io.swagger.pet.model;

import java.util.List;

public class PetResponse {
    long id;
    Category category;
    String name;
    List<String> photoUrls;
    List<Tag> tags;
    String status;

    public long getId() { return id; }
    public Category getCategory() { return category; }
    public String getName() { return name; }
    public List<String> getPhotoUrls() { return photoUrls; }
    public List<Tag> getTags() { return tags; }
    public String getStatus() { return status; }
}
