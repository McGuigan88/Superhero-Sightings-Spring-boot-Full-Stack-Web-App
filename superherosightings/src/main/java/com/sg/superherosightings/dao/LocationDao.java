package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.SuperHero;

import java.util.List;

public interface LocationDao {

    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationByID(int id);

    List<Location> getRecentSightings();

    List<Location> getLocationForSuperHero(SuperHero superhero);
}
