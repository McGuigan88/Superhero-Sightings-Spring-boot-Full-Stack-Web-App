package com.sg.superherosightings.entities;

import java.util.List;
import java.util.Objects;

public class Location {
    private int locationID;
    private String locationName;
    private String locationDescription;
    private String coordinates;

    private List<SuperHero> heros;

    public Location() {
    }

    public Location(int locationID, String locationName, String locationDescription, String coordinates) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        this.coordinates = coordinates;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<SuperHero> getHeros() {
        return heros;
    }

    public void setHeros(List<SuperHero> heros) {
        this.heros = heros;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", locationName='" + locationName + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return locationID == location.locationID && Objects.equals(locationName, location.locationName) && Objects.equals(locationDescription, location.locationDescription) && Objects.equals(coordinates, location.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationID, locationName, locationDescription, coordinates);
    }
}
