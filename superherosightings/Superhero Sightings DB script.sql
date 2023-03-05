drop database if exists SuperHeroDB;

create database SuperHeroDB;

use SuperHeroDB;

-- Tables

create table Superhero (
	SupeID Int auto_increment,
    SupeName Varchar(50) not null,
	SupeDescription Varchar(50) not null,
    Superpower Varchar(50) not null,
    Hero boolean,
    Constraint pk_superhero
		Primary key (SupeID)
);

create table Organization (
	OrganizationID Int auto_increment,
    OrgName Varchar(50) not null,
	TeamDescription Varchar(50) not null,
    ContactInfo Varchar(50) not null,
    Constraint pk_organization
		Primary key (OrganizationID)
);

create table SupeOrganization ( 
	SupeID INT,
    OrganizationID INT,
    CONSTRAINT pk_SupeOrganization
		PRIMARY KEY (SupeID, OrganizationID),
	CONSTRAINT fk_SupeOrganization_Superhero
		FOREIGN KEY (SupeID)
		REFERENCES Superhero(SupeID),
	CONSTRAINT fk_SupeOrganization_Organization
		FOREIGN KEY (OrganizationID)
		REFERENCES Organization(OrganizationID)
);

create table Location (
	LocationID Int auto_increment,
    LocationName Varchar(50) not null,
	LocationDescription Varchar(50) not null,
    Coordinates Varchar(100),
    Constraint pk_Location
		Primary key (LocationID)
);

/*create table HeroLocation ( 
	SupeID INT,
    locationID INT,
    CONSTRAINT pk_HeroLocation
		PRIMARY KEY (SupeID, LocationID),
	CONSTRAINT fk_HeroLocation_Superhero
		FOREIGN KEY (SupeID)
		REFERENCES Superhero(SupeID),
	CONSTRAINT fk_HeroLocation_Location
		FOREIGN KEY (LocationID)
		REFERENCES Location(LocationID)
);*/

CREATE TABLE Sighting (
	sightingID INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate Varchar(10) NOT NULL, 
    locationID INT NOT NULL, 
    supeID INT NOT NULL, 
    FOREIGN KEY fk_Sighting_Location(locationID) REFERENCES Location(LocationID),
    FOREIGN KEY fk_Sighting_Superhero(supeID) REFERENCES Superhero(supeID)
);

select * from sighting;


