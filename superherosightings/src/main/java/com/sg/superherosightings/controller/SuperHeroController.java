package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SuperHeroDao;
import com.sg.superherosightings.entities.SuperHero;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SuperHeroController {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    Set<ConstraintViolation<SuperHero>> violations = new HashSet<>();

    @GetMapping("superheroes")
    public String displaySuperHero(Model model) {
        List<SuperHero> superheroes = superheroDao.getAllHeros();
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("errors", violations);
        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request) {
        String supeName = request.getParameter("supeName");
        String supeDescription = request.getParameter("supeDescription");
        String superPower = request.getParameter("Superpower");
        String heroOrVillian = request.getParameter("hero");
        boolean hero = Boolean.parseBoolean(heroOrVillian);

        SuperHero superhero = new SuperHero();
        superhero.setSupeName(supeName);
        superhero.setSupeDescription(supeDescription);
        superhero.setSuperpower(superPower);
        superhero.setHero(hero);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        if(violations.isEmpty()) {
            violations = validate.validate(superhero);
        }

        superheroDao.addHero(superhero);

        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superheroDao.deleteHeroById(id);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperHero superhero = superheroDao.getHeroById(id);

        model.addAttribute("superhero", superhero);
        model.addAttribute("errors", violations);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperHero superhero = superheroDao.getHeroById(id);

        superhero.setSupeName(request.getParameter("supeName"));
        superhero.setSupeDescription(request.getParameter("supeDescription"));
        superhero.setSuperpower(request.getParameter("Superpower"));
        String heroOrVillain = request.getParameter("hero");
        boolean hero = Boolean.parseBoolean(heroOrVillain);
        superhero.setHero(hero);
        //superhero.setHero(request.getParameter("Hero"));

        superheroDao.updateHero(superhero);

        return "redirect:/superheroes";
    }
}
