package com.sg.superherosightings.entities;

import java.util.List;
import java.util.Objects;

public class Organization {
    private int organizationID;
    private String orgName;
    private String teamDescription;
    private String contactInfo;
    private List<SuperHero> members;

    public Organization() {
    }

    public Organization(int organizationID, String orgName, String teamDescription, String contactInfo, List<SuperHero> members) {
        this.organizationID = organizationID;
        this.orgName = orgName;
        this.teamDescription = teamDescription;
        this.contactInfo = contactInfo;
        this.members = members;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<SuperHero> getMembers() {
        return members;
    }

    public void setMembers(List<SuperHero> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "organizationID=" + organizationID +
                ", orgName='" + orgName + '\'' +
                ", teamDescription='" + teamDescription + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization that)) return false;
        return getOrganizationID() == that.getOrganizationID() && Objects.equals(getOrgName(), that.getOrgName()) && Objects.equals(getTeamDescription(), that.getTeamDescription()) && Objects.equals(getContactInfo(), that.getContactInfo()) && Objects.equals(getMembers(), that.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrganizationID(), getOrgName(), getTeamDescription(), getContactInfo(), getMembers());
    }
}
