package com.maciejlesniak.Dopasowania;

public class DopasowaniaObject {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String description;

    public DopasowaniaObject(String userId, String name, String profileImageUrl, String description) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public DopasowaniaObject(String name, String profileImageUrl, String description) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
