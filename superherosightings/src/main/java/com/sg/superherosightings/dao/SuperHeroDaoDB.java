package com.sg.superherosightings.dao;

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
public class SuperHeroDaoDB implements SuperHeroDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperHero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Superhero WHERE SupeID = ?";
            return jdbc.queryForObject(GET_HERO_BY_ID, new SuperHeroMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperHero> getAllHeros() {
        final String GET_ALL_SUPERHEROS = "SELECT * FROM SuperHero";
        return jdbc.query(GET_ALL_SUPERHEROS, new SuperHeroMapper());
    }

    @Override
    public SuperHero addHero(SuperHero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO SuperHero(SupeName, SupeDescription, Superpower, Hero) " +
                "VALUES(?,?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getSupeName(),
                superhero.getSupeDescription(),
                superhero.getSuperpower(),
                superhero.isHero());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setSupeID(newId);
        return superhero;
    }

    @Override
    public void updateHero(SuperHero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE SuperHero SET SupeName = ?, SupeDescription = ?, Superpower = ?, " +
                "Hero = ? WHERE SupeID = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getSupeName(),
                superhero.getSupeDescription(),
                superhero.getSuperpower(),
                superhero.isHero(),
                superhero.getSupeID());

    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_ORGANIZATION_HERO = "DELETE so.* FROM SupeOrganization so "
                + "JOIN Organization o ON so.OrganizationID = o.OrganizationID WHERE so.SupeID = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, id);

        /*final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE SupeID = ?";
        jdbc.update(DELETE_ORGANIZATION, id);*/

        final String DELETE_LOCATION_HERO = "DELETE hl.* FROM HeroLocation hl "
                + "JOIN location l ON hl.LocationID = l.LocationID WHERE hl.SupeID = ?";
        jdbc.update(DELETE_LOCATION_HERO, id);

        /*final String DELETE_LOCATION= "DELETE FROM location WHERE SupeID = ?";
        jdbc.update(DELETE_LOCATION, id);*/

        final String DELETE_SUPERHERO = "DELETE FROM SuperHero WHERE SupeID = ?";
        jdbc.update(DELETE_SUPERHERO, id);

    }

    public static final class SuperHeroMapper implements RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
            SuperHero superhero = new SuperHero();
            superhero.setSupeID(rs.getInt("SupeID"));
            superhero.setSupeName(rs.getString("SupeName"));
            superhero.setSupeDescription(rs.getString("SupeDescription"));
            superhero.setSuperpower(rs.getString("Superpower"));
            superhero.setHero(rs.getBoolean("Hero"));

            return superhero;
        }
    }
}
