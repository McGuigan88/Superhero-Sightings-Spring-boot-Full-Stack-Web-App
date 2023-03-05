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
public class OrganizationDaoDBTest {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    public OrganizationDaoDBTest() {

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
    public void testAddAndGetOrganization() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Organization organization = new Organization();
        organization.setOrgName("Test organization Name");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test contact info");
        organization.setMembers(superheroes);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationID());
        assertEquals(organization, fromDao);
    }

    @Test
    public void testGetOrganizations() {
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

        Organization organization = new Organization();
        organization.setOrgName("Test organization Name");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test contact info");
        organization.setMembers(superheroes1);
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setOrgName("Test organization Name");
        organization2.setTeamDescription("Test Team description");
        organization2.setContactInfo("Test contact info");
        organization2.setMembers(superheroes2);
        organization2 = organizationDao.addOrganization(organization2);


        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    @Test
    public void testUpdateOrganization() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Organization organization = new Organization();
        organization.setOrgName("Test organization Name");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test contact info");
        organization.setMembers(superheroes);
        organization = organizationDao.addOrganization(organization);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationID());
        assertEquals(organization, fromDao);

        organization.setOrgName("New Test org Name");
        organizationDao.updateOrganization(organization);

        assertNotEquals(organization, fromDao);

        fromDao = organizationDao.getOrganizationById(organization.getOrganizationID());
        assertEquals(organization, fromDao);
    }


    @Test
    public void testDeleteOrganizationById() {
        SuperHero superhero = new SuperHero();
        superhero.setSupeName("Test Name");
        superhero.setSupeDescription("Test Description");
        superhero.setSuperpower("Test superpower");
        superhero.setHero(true);
        superhero = superheroDao.addHero(superhero);
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        Organization organization = new Organization();
        organization.setOrgName("Test organization Name");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test contact info");
        organization.setMembers(superheroes);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationID());
        assertEquals(organization, fromDao);

        organizationDao.deleteOrganizationById(organization.getOrganizationID());

        fromDao = organizationDao.getOrganizationById(organization.getOrganizationID());
        assertNull(fromDao);
    }


    @Test
    public void testGetOrganizationForSuperHero() {
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

        Organization organization = new Organization();
        organization.setOrgName("Test organization Name");
        organization.setTeamDescription("Test Team description");
        organization.setContactInfo("Test contact info");
        organization.setMembers(superheroes1);
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setOrgName("Test organization Name 2");
        organization2.setTeamDescription("Test Team description2");
        organization2.setContactInfo("Test contact info 2");
        organization2.setMembers(superheroes2);
        organization2 = organizationDao.addOrganization(organization2);

        Organization organization3 = new Organization();
        organization3.setOrgName("Test organization Name 3");
        organization3.setTeamDescription("Test Team description 3");
        organization3.setContactInfo("Test contact info 3");
        organization3.setMembers(superheroes2);
        organization3 = organizationDao.addOrganization(organization3);


        List<Organization> organizations1 = organizationDao.getOrganizationsForHero(superhero2);
        List<Organization> organizations2 = organizationDao.getOrganizationsForHero(superhero);

        assertEquals(2, organizations1.size());
        assertTrue(organizations1.contains(organization2));
        assertTrue(organizations1.contains(organization3));
        assertFalse(organizations2.contains(organization3));
        assertTrue(organizations2.contains(organization));
    }
}
