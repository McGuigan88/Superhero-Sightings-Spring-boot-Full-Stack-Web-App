package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SuperHeroDaoDBTest {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    public SuperHeroDaoDBTest() {
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
    public void testAddAndGetSuperHero() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);

        SuperHero fromDao = superheroDao.getHeroById(superhero.getSupeID());

        assertEquals(superhero, fromDao);
    }

    @Test
    public void testGetAllSuperHeros() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);

        SuperHero superhero2 = new SuperHero();
        superhero2.setSupeName("Test Name 2");
        superhero2.setSupeDescription("Test Description 2");
        superhero2.setSuperpower("Test superpower 2");
        superhero2.setHero(true);
        superhero2 = superheroDao.addHero(superhero2);

        List<SuperHero> superheroes = superheroDao.getAllHeros();

        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    @Test
    public void testUpdateSuperHero() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);

        SuperHero fromDao = superheroDao.getHeroById(superhero.getSupeID());
        assertEquals(superhero, fromDao);

        superhero.setSupeName("New Test First");
        superheroDao.updateHero(superhero);

        assertNotEquals(superhero, fromDao);

        fromDao = superheroDao.getHeroById(superhero.getSupeID());

        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperHeroById() {
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

        Organization organization = new Organization();
        organization.setOrgName("Test Organization");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test Contact");
        organization.setMembers(superheroes);
        organization = organizationDao.addOrganization(organization);

        SuperHero fromDao = superheroDao.getHeroById(superhero.getSupeID());
        assertEquals(superhero, fromDao);

        superheroDao.deleteHeroById(superhero.getSupeID());

        fromDao = superheroDao.getHeroById(superhero.getSupeID());
        assertNull(fromDao);
    }

}
