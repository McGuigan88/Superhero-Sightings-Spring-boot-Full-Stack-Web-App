package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SuperHeroDao;
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
public class OrganizationController {
    @Autowired
    SuperHeroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<SuperHero> superheroes = superheroDao.getAllHeros();

        model.addAttribute("organizations", organizations);
        model.addAttribute("superheroes", superheroes);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String[] superheroIds = request.getParameterValues("SupeID");
        String orgName = request.getParameter("orgName");
        String teamDescription = request.getParameter("TeamDescription");
        String contactinfo = request.getParameter("ContactInfo");

        List<SuperHero> superheroes = new ArrayList<>();
        for(String superheroId : superheroIds) {
            superheroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }
        Organization organization = new Organization();

        organization.setOrgName(orgName);
        organization.setTeamDescription(teamDescription);
        organization.setContactInfo(contactinfo);
        organization.setMembers(superheroes);
        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<SuperHero> superHeroes = superheroDao.getAllHeros();
        model.addAttribute("organization", organization);
        model.addAttribute("superheroes", superHeroes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request) {
        String[] superheroIds = request.getParameterValues("SupeID");

        List<SuperHero> superHeroes = new ArrayList<>();
        for(String superheroId : superheroIds) {
            superHeroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }
        organization.setMembers(superHeroes);
        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
}
