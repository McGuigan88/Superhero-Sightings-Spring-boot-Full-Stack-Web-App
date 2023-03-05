package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;

import java.util.List;

public interface OrganizationDao {
    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    List<Organization> getOrganizationsForHero(SuperHero superhero);
}
