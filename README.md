# LOCATION VALIDATOR
@author Archismito Choudhury<br/>
@date 16-07-2023

# OVERVIEW

This is a Springboot application that scrapes the URL "https://www.shoppersstop.com/store-finder" and stores the locations in Database.
Alongside this also verifies if the location is valid or not.


## DEPLOYMENT DETAILS

- Make sure you have the Java 17.0 version installed
- Go to LocationImpl.java.
- At line 81, replace "DUMMY_KEY" (the Google API key) with your actual Google API key
- Go to Germin8Application.java
- Run the file as Java application
- Server will automatically start at port 8080

## API Documentation

    # Scrape the website and save the data
        API-> http://localhost:8080/location
        METHOD -> GET

    This API will scrape the website and save the locations in the database. This is also validated if the location is correct or not.
    In the console, you can see the reason why the location is marked Invalid/Valid.


    # Get invalid locations
        API-> http://localhost:8080/location/invalid-locations
        METHOD -> GET

    This API return the list of all the locations that are invalid.

    Response ->

    [
    {
        "name": "JALANDAR",
        "latitude": "30.883353",
        "longitude": "75.781841"
    },
    {
        "name": "MAC MG1",
        "latitude": "12.973501",
        "longitude": "77.620117"
    },
    {
        "name": "RAIPUR",
        "latitude": "21.255172",
        "longitude": "81.645943"
    },
    {
        "name": "CHENNAI-Palladium",
        "latitude": "12.9923881",
        "longitude": "80.1820116"
    }
    ]


# Conditions on which a location is marked invalid

- Location not found by Google Places API
- Name does not match "Shoppers Stop"
- Name does not contain the string "Shoppers Stop"


# Database setup

Schema -> location_schema

Query -> 

CREATE TABLE `location` (
  `pk_location_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
  `latitude` VARCHAR(255) DEFAULT NULL,
  `longitude` VARCHAR(255) DEFAULT NULL,
  `is_valid` TINYINT(1) DEFAULT '1',
  `is_deleted` TINYINT(1) DEFAULT '0',
  PRIMARY KEY (`pk_location_id`)
)

# Tech Stack 
- Java 17
- Springboot
- Hibernate JPA
- MySQL
- Places API by Google
- Jsoup for scraping website

# Contact information

- Linkedin: https://www.linkedin.com/in/archismito-choudhury-6a58771b7/
- Email: archischamps@gmail.com
