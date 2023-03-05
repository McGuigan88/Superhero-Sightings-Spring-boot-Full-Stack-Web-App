package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class locationDaoDB implements LocationDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE LocationID = ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID , new LocationMapper(), id);
            location.setHeros(getSuperHerosForLocation(id));
            return location;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    private List<SuperHero> getSuperHerosForLocation(int id) {
        final String SELECT_SUPERHEROS_FOR_LOCATION = "SELECT s.* FROM SuperHero s "
                + "JOIN HeroLocation hl ON hl.SupeID = s.SupeID WHERE hl.LocationID = ?";
        return jdbc.query(SELECT_SUPERHEROS_FOR_LOCATION, new SuperHeroDaoDB.SuperHeroMapper(), id);
    }

    @Override
    public List<Location> getRecentSightings(){
        final String SELECT_RECENTSIGHTINGS = "SELECT * FROM Location ORDER BY LocationID DESC limit 10";
        List<Location> sightings = jdbc.query( SELECT_RECENTSIGHTINGS, new LocationMapper());
        return sightings;
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
        List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
        associateSuperHeros(locations);
        return locations;
    }
    private void associateSuperHeros(List<Location> locations) {
        for (Location location : locations) {
            location.setHeros(getSuperHerosForLocation(location.getLocationID()));
        }
    }

    @Override
    public Location addLocation(Location location) {
        final String INSERT_COURSE = "INSERT INTO Location(LocationName, LocationDescription, Coordinates) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_COURSE,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getCoordinates());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);
        insertHeroLocation(location);
        return location;
    }

    private void insertHeroLocation(Location location) {
        final String INSERT_LOCATION_SUPERHERO = "INSERT INTO "
                + "HeroLocation(SupeID, LocationID) VALUES(?,?)";
        for(SuperHero superhero : location.getHeros()) {
            jdbc.update(INSERT_LOCATION_SUPERHERO,
                    superhero.getSupeID(),
                    location.getLocationID());
        }
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET LocationName = ?, LocationDescription = ?, "
                + "Coordinates = ? WHERE LocationID = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getCoordinates(),
                location.getLocationID());

        final String DELETE_LOCATION_SUPERHERO = "DELETE FROM HeroLocation WHERE LocationID = ?";
        jdbc.update(DELETE_LOCATION_SUPERHERO , location.getLocationID());
        insertHeroLocation(location);
    }

    @Override
    public void deleteLocationByID(int id) {
        final String DELETE_LOCATION_SUPERHERO = "DELETE FROM HeroLocation WHERE LocationID = ?";
        jdbc.update(DELETE_LOCATION_SUPERHERO, id);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE LocationID = ?";
        jdbc.update(DELETE_LOCATION, id);


    }

    @Override
    public List<Location> getLocationForSuperHero(SuperHero superhero) {
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM Location l JOIN "
                + "HeroLocation hl ON hl.LocationID = l.LocationID WHERE hl.SupeID = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO,
                new LocationMapper(), superhero.getSupeID());
        associateSuperHeros(locations);
        return locations;
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getInt("LocationID"));
            location.setLocationName(rs.getString("LocationName"));
            location.setLocationDescription(rs.getString("LocationDescription"));
            location.setCoordinates(rs.getString("Coordinates"));
            return location;
        }
    }
}
