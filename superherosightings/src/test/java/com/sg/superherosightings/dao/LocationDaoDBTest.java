package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationDaoDBTest {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    public LocationDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<SuperHero> superheros = superheroDao.getAllHeros();
        for(SuperHero hero : superheros) {
            superheroDao.deleteHeroById(hero.getSupeID());
        }

        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationByID(location.getLocationID());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrganizationID());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location description");
        location.setCoordinates("47.65100, -122.34900, 4326");
        location.setHeros(superheroes);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);
    }


    @Test
    public void testGetAllLocations() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location description");
        location.setCoordinates("47.65100, -122.34900, 4326");
        location.setHeros(superheroes);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setLocationName("Test Location Name 2");
        location2.setLocationDescription("Test Location description 2");
        location2.setCoordinates("21.84601, -124.73548, 3648");
        location2.setHeros(superheroes);
        location2 = locationDao.addLocation(location2);


        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location description");
        location.setCoordinates("47.65100, -122.34900, 4326");
        location.setHeros(superheroes);
        location = locationDao.addLocation(location);

        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Location fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);

        location.setLocationName("New Test Location Name");
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location description");
        location.setCoordinates("47.65100, -122.34900, 4326");
        location.setHeros(superheroes);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);

        locationDao.deleteLocationByID(location.getLocationID());

        fromDao = locationDao.getLocationById(location.getLocationID());
        assertNull(fromDao);
    }

    @Test
    public void testGetLocationForSuperHero() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes1 = new ArrayList<>();
        superheroes1.add(superhero);

        SuperHero superhero2 = new SuperHero();
        superhero2.setSupeName("Test Name 2");
        superhero2.setSupeDescription("Test Description 2");
        superhero2.setSuperpower("Test superpower");
        superhero2.setHero(false);
        superhero2 = superheroDao.addHero(superhero2);
        List<SuperHero> superheroes2 = new ArrayList<>();
        superheroes2.add(superhero2);

        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location description");
        location.setCoordinates("47.65100, -122.34900, 4326");
        location.setHeros(superheroes1);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setLocationName("Test Location Name 2");
        location2.setLocationDescription("Test Location description 2");
        location2.setCoordinates("21.84601, -124.73548, 3648");
        location2.setHeros(superheroes2);
        location2 = locationDao.addLocation(location2);

        Location location3 = new Location();
        location3.setLocationName("Test Location Name 3");
        location3.setLocationDescription("Test Location description 3");
        location3.setCoordinates("88.92746, -974.35678, 9136");
        location3.setHeros(superheroes2);
        location3 = locationDao.addLocation(location3);


        List<Location> locations1 = locationDao.getLocationForSuperHero(superhero2);
        List<Location> locations2 = locationDao.getLocationForSuperHero(superhero);
        assertEquals(2, locations1.size());
        assertTrue(locations1.contains(location2));
        assertTrue(locations1.contains(location3));
        assertFalse(locations2.contains(location3));
        assertTrue(locations2.contains(location));
    }
}
