package com.sg.superherosightings.entities;

import java.util.Objects;
import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SuperHero {
    private int supeID;

    @NotBlank(message = "SuperHero name must not be empty.")
    @Size(max = 50, message="SuperHero name must be less than 50 characters.")
    private String supeName;

    @NotBlank(message = "Hero description must not be empty.")
    @Size(max = 200, message="Hero description must be less than 200 characters.")
    private String supeDescription;

    @NotBlank(message = "Superpower must not be empty.")
    @Size(max = 50, message="Superpower must be less than 50 characters.")
    private String superpower;

    @NotBlank(message = "Hero must not be empty.")
    private boolean hero;

    public SuperHero() {

    }

    public SuperHero(int supeID, String supeName, String supeDescription,String superpower, boolean hero) {
        this.supeID = supeID;
        this.supeName = supeName;
        this.supeDescription = supeDescription;
        this.superpower = superpower;
        this.hero = hero;
    }

    public int getSupeID() {
        return supeID;
    }

    public void setSupeID(int supeID) {
        this.supeID = supeID;
    }

    public String getSupeName() {
        return supeName;
    }

    public void setSupeName(String supeName) {
        this.supeName = supeName;
    }

    public String getSupeDescription() {
        return supeDescription;
    }

    public void setSupeDescription(String supeDescription) {
        this.supeDescription = supeDescription;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    @Override
    public String toString() {
        return "SuperHero{" +
                "supeID=" + supeID +
                ", supeName='" + supeName + '\'' +
                ", supeDescription='" + supeDescription + '\'' +
                ", superpower='" + superpower + '\'' +
                ", hero=" + hero +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperHero superHero)) return false;
        return getSupeID() == superHero.getSupeID() && isHero() == superHero.isHero() && Objects.equals(getSupeName(), superHero.getSupeName()) && Objects.equals(getSupeDescription(), superHero.getSupeDescription()) && Objects.equals(getSuperpower(), superHero.getSuperpower());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSupeID(), getSupeName(), getSupeDescription(), getSuperpower(), isHero());
    }
}
