package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SuperHeroDao;
import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.SuperHero;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocationController {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        return "locations";
    }

    @GetMapping("/")
    public String displayLastTenSightings(Model model){
        List<Location> locations = locationDao.getRecentSightings();
        List<SuperHero> superheroes = superheroDao.getAllHeros();
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        return "index";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String[] superheroIds = request.getParameterValues("SupeID");
        String locationName = request.getParameter("LocationName");
        String locationDescription = request.getParameter("LocationDescription");
        String coordinates = request.getParameter("Coordinates");

        List<SuperHero> superheroes = new ArrayList<>();
        for(String superheroId : superheroIds) {
            superheroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }

        Location location = new Location();
        location.setLocationName(locationName);
        location.setLocationDescription(locationDescription);
        location.setCoordinates(coordinates);
        location.setHeros(superheroes);
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        List<SuperHero> superHeroes = superheroDao.getAllHeros();
        model.addAttribute("location", location);
        model.addAttribute("superheroes", superHeroes);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(Location location, HttpServletRequest request) {
        String[] superheroIds = request.getParameterValues("SupeID");

        List<SuperHero> superHeroes = new ArrayList<>();
        for(String superheroId : superheroIds) {
            superHeroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }
        location.setHeros(superHeroes);
        locationDao.updateLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationByID(id);
        return "redirect:/locations";
    }
}
