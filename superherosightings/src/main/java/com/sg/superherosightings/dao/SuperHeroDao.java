package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.SuperHero;

import java.util.List;

public interface SuperHeroDao {

    SuperHero getHeroById(int id);

    List<SuperHero> getAllHeros();

    SuperHero addHero(SuperHero superhero);

    void updateHero(SuperHero superhero);

    void deleteHeroById(int id);

}
