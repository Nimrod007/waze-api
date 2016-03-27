package com.waze.domain;

public class WazeAlert {

    private String country;
    private int numOfThumbsUp;
    private String type;
    private String subType;
    private String placeNearBy;
    private String latitude; // Y
    private String longitude; // X

    public WazeAlert(String country, int numOfThumbsUp, String type, String subType, String placeNearBy, String latitude, String longitude) {
        this.country = country;
        this.numOfThumbsUp = numOfThumbsUp;
        this.type = type;
        this.subType = subType;
        this.placeNearBy = placeNearBy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WazeAlert() {
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNumOfThumbsUp(int numOfThumbsUp) {
        this.numOfThumbsUp = numOfThumbsUp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setPlaceNearBy(String placeNearBy) {
        this.placeNearBy = placeNearBy;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public int getNumOfThumbsUp() {
        return numOfThumbsUp;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getPlaceNearBy() {
        return placeNearBy;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
