package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganizationDaoDB implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organization WHERE OrganizationID = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setMembers(getSuperHerosForOrganization(id));
            return organization;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    private List<SuperHero> getSuperHerosForOrganization(int id) {
        final String SELECT_SUPERHEROS_FOR_ORGANIZATION = "SELECT s.* FROM SuperHero s "
                + "JOIN SupeOrganization so ON so.SupeID = s.SupeID WHERE so.OrganizationID = ?";
        return jdbc.query(SELECT_SUPERHEROS_FOR_ORGANIZATION, new SuperHeroDaoDB.SuperHeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSuperHeros(organizations);
        return organizations;
    }
    private void associateSuperHeros(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setMembers(getSuperHerosForOrganization(organization.getOrganizationID()));
        }
    }

    @Override
    public List<Organization> getOrganizationsForHero(SuperHero superhero) {
        final String SELECT_COURSES_FOR_SUPERHERO = "SELECT o.* FROM Organization o JOIN "
                + "SupeOrganization so ON so.OrganizationID = o.OrganizationID WHERE so.SupeID = ?";
        List<Organization> organizations = jdbc.query(SELECT_COURSES_FOR_SUPERHERO,
                new OrganizationMapper(), superhero.getSupeID());
        associateSuperHeros(organizations);
        return organizations;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_COURSE = "INSERT INTO Organization(OrgName, TeamDescription, ContactInfo) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_COURSE,
                organization.getOrgName(),
                organization.getTeamDescription(),
                organization.getContactInfo());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationID(newId);
        insertSupeOrganization(organization);
        return organization;
    }

    private void insertSupeOrganization(Organization organization) {
        final String INSERT_ORGANIZATION_SUPERHERO = "INSERT INTO "
                + "SupeOrganization(SupeID, OrganizationID) VALUES(?,?)";
        for(SuperHero superhero : organization.getMembers()) {
            jdbc.update(INSERT_ORGANIZATION_SUPERHERO,
                    superhero.getSupeID(),
                    organization.getOrganizationID());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE Organization SET OrgName = ?, TeamDescription = ?, "
                + "ContactInfo = ? WHERE OrganizationID = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getOrgName(),
                organization.getTeamDescription(),
                organization.getContactInfo(),
                organization.getOrganizationID());

        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM SupeOrganization WHERE OrganizationID = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO , organization.getOrganizationID());
        insertSupeOrganization(organization);

    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM SupeOrganization WHERE OrganizationID = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);

        final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE OrganizationID = ?";
        jdbc.update(DELETE_ORGANIZATION, id);

    }


    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationID(rs.getInt("OrganizationID"));
            organization.setOrgName(rs.getString("OrgName"));
            organization.setTeamDescription(rs.getString("TeamDescription"));
            organization.setContactInfo(rs.getString("ContactInfo"));
            return organization;
        }
    }
}
