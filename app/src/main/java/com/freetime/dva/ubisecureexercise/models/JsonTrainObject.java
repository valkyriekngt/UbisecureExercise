package com.freetime.dva.ubisecureexercise.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonTrainObject {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public double getLatitude(){
        double lat = coordinates.get(0);
        return lat;
    }
    public double getLongitude(){
        double lon = coordinates.get(1);
        return lon;
    }

}

